package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.BadRequestException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductFilterByCategoryDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductFilterByUserDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

/*
 * Implementación del servicio de productos.
 *
 * Gestiona productos con relaciones hacia usuarios y categorías.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;

    }

    /*
     * Retorna los productos activos creados por un usuario.
     *
     * Primero valida que el usuario exista y no esté eliminado.
     */
    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {
        if (!userRepository.existsByIdAndDeletedFalse(userId)) {
            throw new NotFoundException("User not found");
        }

        List<ProductEntity> list = productRepository.findByOwner_IdAndDeletedFalse(userId);

        return list
                .stream()
                .map(ProductMapper::toModel)
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    /*
     * Crea un producto asociado a un usuario y a una categoría.
     *
     * Valida:
     * - que el usuario exista
     * - que la categoría exista
     * - que no exista un producto activo con el mismo nombre
     */
    @Override
    public ProductResponseDto create(CreateProductDto dto) {

        // 1 Encontramos el user
        UserEntity owner = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (owner.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        // 2 Encontramos la categoria
        // El metodo de creación ahora debe que las categorías sean validadas (existan y no estén eliminadas) para poderasociar al producto.
        Set<CategoryEntity> categories = validateAndGetCategories(dto.getCategoryIds());
    
        // validadacion de negocio, por ejemplo que no exista un producto con el mismo
        // nombre
        if (productRepository.findByNameIgnoreCaseAndDeletedFalse(dto.getName()).isPresent()) {
            throw new ConflictException("Product name already registered");
        }

        // Genereamos la entidad a partir del DTO

        ProductEntity entity = new ProductEntity();

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setOwner(owner);
        entity.setCategories(categories);

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel savedModel = ProductMapper.toModel(savedEntity);

        return ProductMapper.toResponseDto(savedModel);
    }

    /*
     * Actualiza completamente un producto activo.
     *
     * No permite cambiar el usuario propietario.
     * Sí permite cambiar la categoría.
     */
    @Override
    public ProductResponseDto update(Long id, UpdateProductDto dto) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Set<CategoryEntity> categories = validateAndGetCategories(dto.getCategoryIds());

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setCategories(categories);

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel model = ProductMapper.toModel(savedEntity);

        return ProductMapper.toResponseDto(model);
    }

    /*
     * Actualiza parcialmente un producto activo.
     *
     * Solo modifica los campos enviados en el DTO.
     */
    @Override
    public ProductResponseDto partialUpdate(Long id, PartialUpdateProductDto dto) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }

        if (dto.getStock() != null) {
            entity.setStock(dto.getStock());
        }

        Set<CategoryEntity> categories = validateAndGetCategories(dto.getCategoryIds());

        entity.setCategories(categories);
        

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel model = ProductMapper.toModel(savedEntity);

        return ProductMapper.toResponseDto(model);
    }

    @Override
    public List<ProductResponseDto> findAll() {

        return productRepository.findByDeletedFalse()
                .stream()
                .map(ProductMapper::toModel)
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(Long id) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return ProductMapper.toResponseDto(ProductMapper.toModel(entity));
    }

    @Override
    public void delete(Long id) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        entity.setDeleted(true);

        productRepository.save(entity);
    }

    /*
     * Retorna productos activos de un usuario aplicando filtros opcionales.
     *
     * Primero valida que el usuario exista y no esté eliminado.
     * Luego valida el rango de precios.
     * Finalmente consulta los productos desde ProductRepository.
     */
    @Override
    public List<ProductResponseDto> findByUserIdWithFilters(
            Long userId,
            ProductFilterByUserDto filters) {
        if (!userRepository.existsByIdAndDeletedFalse(userId)) {
            throw new NotFoundException("User not found");
        }

        validateUserFilters(filters);

        String name = normalizeName(filters.getName());

        return productRepository.findByOwnerIdWithFilters(
                userId,
                name,
                filters.getMinPrice(),
                filters.getMaxPrice(),
                filters.getUserId())
                .stream()
                .map(ProductMapper::toModel)
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    /*
     * Retorna productos activos de una categoría aplicando filtros opcionales.
     *
     * Primero valida que la categoría exista y no esté eliminada.
     * Luego valida el rango de precios.
     * Si viene userId como filtro, valida que el usuario exista.
     * Finalmente consulta los productos desde ProductRepository.
     */
    @Override
    public List<ProductResponseDto> findByCategoryIdWithFilters(
            Long categoryId,
            ProductFilterByCategoryDto filters) {
        if (!categoryRepository.existsByIdAndDeletedFalse(categoryId)) {
            throw new NotFoundException("Category not found");
        }

        validateCategoryFilters(filters);

        String name = normalizeName(filters.getName());

        return productRepository.findByCategoryIdWithFilters(
                categoryId,
                name,
                filters.getMinPrice(),
                filters.getMaxPrice(),
                filters.getCategoryId())
                .stream()
                .map(ProductMapper::toModel)
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    /*
     * Valida reglas de negocio relacionadas con filtros.
     */
    private void validateUserFilters(ProductFilterByUserDto filters) {

        if (filters == null) {
            return;
        }

        if (!filters.hasValidPriceRange()) {
            throw new BadRequestException("El precio máximo debe ser mayor o igual al precio mínimo");
        }
    }

    /*
     * Valida reglas de negocio relacionadas con filtros
     * usados desde el contexto de categoría.
     */
    // CREA EL METODO QUE VALIDA LOS PARAMETROS DE FILTRO PARA CATEGORIA
    private void validateCategoryFilters(ProductFilterByCategoryDto filters) {

        if (filters == null) {
            return;
        }

        if (!filters.hasValidPriceRange()) {
            throw new BadRequestException("El precio máximo debe ser mayor o igual al precio mínimo");
        }
    }

    /*
 * Valida que todas las categorías existan y estén activas.
 *
 * Retorna el conjunto de entidades CategoryEntity
 * que se asociarán al producto.
 */
    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {

        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new BadRequestException("Debe seleccionar al menos una categoría");
        }

        Set<CategoryEntity> categories = new HashSet<>();

        for (Long categoryId : categoryIds) {
            CategoryEntity category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Category not found"));

            if (category.isDeleted()) {
                throw new NotFoundException("Category not found");
            }

            categories.add(category);
        }

        return categories;
    }

    /*
     * Convierte un texto vacío en null.
     *
     * Esto permite que el repositorio ignore el filtro por nombre
     * cuando el query param llega vacío.
     */
    private String normalizeName(String name) {

        if (name == null || name.isBlank()) {
            return null;
        }

        return name.trim();
    }

}
