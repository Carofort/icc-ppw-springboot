package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

/**
 * DTO utilizado para devolver al cliente los datos públicos
 * de un producto como respuesta de la API.
 */
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private UserResponseDto owner;

     private List<CategoryResponseDto> categories;   

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public ProductResponseDto() {
    }

    public ProductResponseDto(Long id, String name, String description, Double price, Integer stock, UserResponseDto owner, List<CategoryResponseDto> categories, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.owner = owner;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public UserResponseDto getOwner() { return owner; }
    public void setOwner(UserResponseDto owner) { this.owner = owner; }

    public List<CategoryResponseDto> getCategories() { return categories; }
    public void setCategories(List<CategoryResponseDto> categories) { this.categories = categories; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}