package ec.edu.ups.icc.fundamentos01.products.entities;

import java.util.HashSet;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    /*
     * Relación muchos a uno con UserEntity.
     *
     * Muchos productos pueden pertenecer a un usuario.
     * La columna user_id se crea en la tabla products.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    /*
     * Relación muchos a muchos entre productos y categorías.
     *
     * Un producto puede pertenecer a varias categorías.
     * Una categoría puede tener varios productos.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();

    // Constructor vacío
    public ProductEntity() {
    }

    // Constructor lleno
    public ProductEntity(String name, String description, Double price, Integer stock, UserEntity owner,
            Set<CategoryEntity> categories) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.owner = owner;
        this.categories = categories;
    }

    // Getters y Setters
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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }
}