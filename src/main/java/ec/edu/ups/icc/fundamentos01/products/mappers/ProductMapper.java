package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductModel toModel(CreateProductDto dto) {

        if (dto == null)
            return null;

        return new ProductModel(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock());
    }

    public ProductEntity toEntity(ProductModel model) {

        if (model == null)
            return null;

        ProductEntity entity = new ProductEntity();

        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());

        if (model.getId() != null)
            entity.setId(model.getId());

        return entity;
    }

    public ProductModel toModel(ProductEntity entity) {

        if (entity == null)
            return null;

        ProductModel model = new ProductModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setPrice(entity.getPrice());
        model.setStock(entity.getStock());
        model.setCreatedAt(entity.getCreatedAt());

        model.setOwner(entity.getOwner());
        model.setCategory(entity.getCategory());

        return model;
    }

    public ProductResponseDto toResponseDto(ProductModel model) {

        if (model == null)
            return null;

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setPrice(model.getPrice());
        dto.setStock(model.getStock());
        dto.setCreatedAt(model.getCreatedAt());

        if (model.getOwner() != null) {

            var owner = (ec.edu.ups.icc.fundamentos01.users.entities.UserEntity) model.getOwner();

            UserResponseDto ownerDto = new UserResponseDto();

            ownerDto.setId(owner.getId());
            ownerDto.setName(owner.getName());
            ownerDto.setEmail(owner.getEmail());

            dto.setOwner(ownerDto);
        }

        if (model.getCategory() != null) {

            var category = (ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity) model.getCategory();

            CategoryResponseDto categoryDto = new CategoryResponseDto();

            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());

            dto.setCategory(categoryDto);
        }

        return dto;
    }
}