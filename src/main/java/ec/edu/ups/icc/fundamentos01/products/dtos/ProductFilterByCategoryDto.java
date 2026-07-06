package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ProductFilterByCategoryDto {

    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    private String name;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio mínimo no puede ser negativo")
    private Double minPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio máximo no puede ser negativo")
    private Double maxPrice;

    @Min(value = 1, message = "El ID de categoría debe ser mayor a 0")
    private Long categoryId;

    /*
     * Valida que el rango de precios sea coherente.
     */
    public boolean hasValidPriceRange() {
        if (minPrice != null && maxPrice != null) {
            return maxPrice >= minPrice;
        }

        return true;
    }

    public ProductFilterByCategoryDto() {
    }

    public ProductFilterByCategoryDto(String name, Double minPrice, Double maxPrice, Long categoryId) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;    
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
