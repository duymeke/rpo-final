package com.example.demo.mappers;

import com.example.demo.dto.CategoryDto;
import com.example.demo.models.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    private CategoryMapper mapper;

    @Test
    void convertEntityToDtoTest() {
        Category entity = new Category(1L, "Category");

        CategoryDto dto = mapper.toDto(entity);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getName());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getName(), dto.getName());


    }

    @Test
    void convertDtoToEntityTest() {

        CategoryDto dto = new CategoryDto(1L, "Category");

        Category entity = mapper.toEntity(dto);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getName());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getName(), dto.getName());


    }

    @Test
    void convertEntityListToDtoList() {

        List<Category> entities = new ArrayList<>();
        entities.add(new Category(1L, "Cat1"));
        entities.add(new Category(2L, "Cat2"));
        entities.add(new Category(3L, "Cat3"));

        List<CategoryDto> dtos = mapper.toDtoList(entities);

        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(dtos.size(), 0);
        Assertions.assertEquals(dtos.size(), entities.size());

        for (int i=0; i< dtos.size(); i++) {
            Assertions.assertNotNull(dtos.get(i));
            Assertions.assertNotNull(dtos.get(i).getId());
            Assertions.assertNotNull(dtos.get(i).getName());

            Assertions.assertEquals(dtos.get(i).getId(), entities.get(i).getId());
            Assertions.assertEquals(dtos.get(i).getName(), entities.get(i).getName());
        }


    }

}
