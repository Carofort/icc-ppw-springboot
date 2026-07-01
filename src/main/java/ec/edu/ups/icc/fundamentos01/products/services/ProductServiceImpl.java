package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
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

    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
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
                .map(productMapper::toModel)
                .map(productMapper::toResponseDto)
                .toList();
    }

    /*
     * Retorna los productos activos asociados a una categoría.
     *
     * Primero valida que la categoría exista y no esté eliminada.
     */
    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {

        if (!categoryRepository.existsByIdAndDeletedFalse(categoryId)) {
            throw new NotFoundException("Category not found");
        }

        return productRepository.findByCategory_IdAndDeletedFalse(categoryId)
                .stream()
                .map(productMapper::toModel)
                .map(productMapper::toResponseDto)
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
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if (category.isDeleted()) {
            throw new NotFoundException("Category not found");
        }

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
        entity.setCategory(category);

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel savedModel = productMapper.toModel(savedEntity);

        return productMapper.toResponseDto(savedModel);
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

        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if (category.isDeleted()) {
            throw new NotFoundException("Category not found");
        }

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setCategory(category);

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel model = productMapper.toModel(savedEntity);

        return productMapper.toResponseDto(model);
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

        if (dto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));

            if (category.isDeleted()) {
                throw new NotFoundException("Category not found");
            }

            entity.setCategory(category);
        }

        ProductEntity savedEntity = productRepository.save(entity);

        ProductModel model = productMapper.toModel(savedEntity);

        return productMapper.toResponseDto(model);
    }

    @Override
    public List<ProductResponseDto> findAll() {

        return productRepository.findByDeletedFalse()
                .stream()
                .map(productMapper::toModel)
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(Long id) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toResponseDto(productMapper.toModel(entity));
    }

    @Override
    public void delete(Long id) {

        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        entity.setDeleted(true);

        productRepository.save(entity);
    }
}
