package ec.edu.ups.icc.fundamentos01.products.models;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;

/**
 * Modelo de dominio del recurso products.
 * * Representa al producto dentro de la lógica de negocio.
 * No es una entidad de base de datos y no debe tener anotaciones JPA.
 */
public class ProductModel {

    /**
     * Identificador del producto.
     */
    private Long id;
    
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private Object owner; // Cambiado a Object para evitar dependencias directas
    private List<CategoryEntity> categories;

    // Constructor vacío
    public ProductModel() {
    }

    // Constructor lleno para transformaciones básicas
    public ProductModel(String name, String description, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Constructor completo
    public ProductModel(Long id, String name, String description, Double price, Integer stock, LocalDateTime createdAt, Object owner, List<CategoryEntity> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.owner = owner;
        this.categories = categories;
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public Double getPrice() { 
        return price; 
    }

    public void setPrice(Double price) { 
        this.price = price; 
    }

    public Integer getStock() { 
        return stock; 
    }

    public void setStock(Integer stock) { 
        this.stock = stock; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public Object getOwner() { 
        return owner; 
    }

    public void setOwner(Object owner) { 
        this.owner = owner; 
    }

    public List<CategoryEntity> getCategories() { 
        return categories; 
    }

    public void setCategories(List<CategoryEntity> categories) { 
        this.categories = categories; 
    }
}