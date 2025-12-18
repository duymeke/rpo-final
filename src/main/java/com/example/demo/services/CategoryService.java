package com.example.demo.services;


import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.TaskDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();
    CategoryDto getById(Long id);
    CategoryDto create(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    boolean delete(Long id);
}
