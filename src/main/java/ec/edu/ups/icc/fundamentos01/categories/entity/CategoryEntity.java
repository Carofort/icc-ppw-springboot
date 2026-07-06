package ec.edu.ups.icc.fundamentos01.categories.entity;

import jakarta.persistence.Table;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseEntity;
import jakarta.persistence.Entity;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    private String name;
    private String description;


    public CategoryEntity() {
    }

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
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
    
}
