package com.example.demo.mappers;

import com.example.demo.dto.CategoryDto;
import com.example.demo.models.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);

    List<CategoryDto> toDtoList(List<Category> categories);
}