package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductFilterByUserDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;
import jakarta.validation.Valid;

/*
 * Controlador REST encargado de exponer los endpoints HTTP
 * para la gestión de usuarios.
 *
 */
/*
 * Controlador REST encargado de exponer los endpoints HTTP
 * para la gestión de usuarios.
 *
 * En esta práctica el controlador ya no contiene la lógica del CRUD.
 * Solo recibe la petición y delega la operación al servicio.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService service;

    /*
     * Inyección de dependencias por constructor.
     *
     * Spring Boot busca una implementación de UserService,
     * encuentra UserServiceImpl porque tiene @Service,
     * crea el objeto y lo inyecta automáticamente.
     */
    public UsersController(UserService service) {
        this.service = service;
    }

    /*
     * Endpoint para listar todos los usuarios.
     *
     * GET /users
     */
    @GetMapping
    public List<UserResponseDto> findAll() {
        return service.findAll();
    }

    /*
     * Endpoint para buscar un usuario por id.
     *
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    public Object findOne(@PathVariable("id") Long id) {
        return service.findOne(id);
    }

    /*
     * Endpoint para crear un nuevo usuario.
     *
     * POST /users
     */
    @PostMapping
    public UserResponseDto create(@Valid @RequestBody CreateUserDto dto) {
        return service.create(dto);
    }

    /*
     * Endpoint para actualizar completamente un usuario.
     *
     * PUT /users/{id}
     */
    @PutMapping("/{id}")
    public Object update(
            @Valid @PathVariable("id") Long id,
            @RequestBody UpdateUserDto dto) {
        return service.update(id, dto);
    }

    /*
     * Endpoint para actualizar parcialmente un usuario.
     *
     * PATCH /users/{id}
     */
    @PatchMapping("/{id}")
    public Object partialUpdate(
            @Valid @PathVariable("id") Long id,
            @RequestBody PartialUpdateUserDto dto) {
        return service.partialUpdate(id, dto);
    }

    /*
     * Endpoint para eliminar un usuario.
     *
     * DELETE /users/{id}
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    /*
     * Controlador REST encargado de exponer consultas relacionadas
     * entre usuarios y productos.
     *
     * La ruta pertenece al contexto semántico de usuarios:
     * /users/{id}/products
     *
     * Sin embargo, la lógica se delega a ProductService
     * porque el recurso consultado es products.
     */
    @RestController
    @RequestMapping("/users")
    public class UserProductsController {

        private final ProductService productService;

        public UserProductsController(

                ProductService productService) {
            this.productService = productService;
        }

        /*
         * Endpoint para consultar productos de un usuario.
         *
         * GET /api/users/{id}/products
         * GET /api/users/{id}/products?name=laptop
         * GET /api/users/{id}/products?minPrice=500&maxPrice=1500
         * GET /api/users/{id}/products?categoryId=2
         */
        @GetMapping("/{id}/products")
        public List<ProductResponseDto> findProductsByUser(
                @PathVariable Long id,
                @Valid @ModelAttribute ProductFilterByUserDto filters) {
            return productService.findByUserIdWithFilters(id, filters);
        }
    }
}