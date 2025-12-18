package com.example.demo.services.impl;


import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.mappers.CategoryMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;


    @Override
    public List<CategoryDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public CategoryDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public CategoryDto create(CategoryDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category old = repository.findById(id).orElse(null);

        if (Objects.isNull(dto) || Objects.isNull(old)) {
            return null;
        }

        old.setName(dto.getName());

        return mapper.toDto(repository.save(old));
    }

    @Override
    public boolean delete(Long id) {

        repository.deleteById(id);

        CategoryDto delete = getById(id);

        if (Objects.isNull(delete)) {
            return true;
        } else {
            return false;
        }
    }
}
