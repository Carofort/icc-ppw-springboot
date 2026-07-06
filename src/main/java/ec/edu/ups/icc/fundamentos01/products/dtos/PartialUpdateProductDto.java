package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para recibir los datos que se desean
 * actualizar parcialmente en un producto existente (PATCH).
 */
public class PartialUpdateProductDto {

    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", message = "El precio mínimo debe ser 0")
    private Double price;

    @Min(value = 0, message = "El stock mínimo debe ser 0")
    private Integer stock;

        private Set<Long> categoryIds;

    public PartialUpdateProductDto() {
    }

    public PartialUpdateProductDto(String name, String description, Double price, Integer stock, Set<Long> categoryIds) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryIds = categoryIds;
    }

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Set<Long> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(Set<Long> categoryIds) { this.categoryIds = categoryIds; }
}