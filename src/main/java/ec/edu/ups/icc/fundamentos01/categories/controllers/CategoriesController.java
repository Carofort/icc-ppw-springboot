package ec.edu.ups.icc.fundamentos01.categories.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.UpdateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.services.CategoryService;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductFilterByCategoryDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;

/*
 * Controlador REST encargado de exponer los endpoints HTTP
 * para la gestión de categorías.
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoryService service;

    public CategoriesController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto findOne(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public CategoryResponseDto create(@Valid @RequestBody CreateCategoryDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /*
     * Controlador REST encargado de exponer consultas relacionadas
     * entre categorías y productos.
     *
     * La ruta pertenece al contexto semántico de categorías:
     * /categories/{id}/products
     *
     * La lógica se delega a ProductService porque el recurso consultado
     * es products.
     */
    @RestController
    @RequestMapping("/categories")
    public class CategoryProductsController {

        private final ProductService productService;

        public CategoryProductsController(ProductService productService) {
            this.productService = productService;
        }

        /*
         * Endpoint para consultar productos de una categoría.
         *
         * GET /api/categories/{id}/products
         * GET /api/categories/{id}/products?name=laptop
         * GET /api/categories/{id}/products?minPrice=500&maxPrice=1500
         * GET /api/categories/{id}/products?userId=1
         */
        @GetMapping("/{id}/products")
        public List<ProductResponseDto> findProductsByCategory(
                @PathVariable Long id,
                @Valid @ModelAttribute ProductFilterByCategoryDto filters) {
            return productService.findByCategoryIdWithFilters(id, filters);
        }
    }
}
