package com.example.demo.services.impl;


import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final UserMapper mapper;


    @Override
    public List<UserDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        if (Objects.isNull(userCreateDto)) {
            return null;
        }

        return mapper.toDto(repository.save(mapper.toEntity(userCreateDto)));
    }

    @Override
    public UserDto update(Long id, UserCreateDto userCreateDto) {
        User old = repository.findById(id).orElse(null);

        if (Objects.isNull(userCreateDto) || Objects.isNull(old)) {
            return null;
        }

        old.setUsername(userCreateDto.getUsername());
        old.setEmail(userCreateDto.getUsername());
        old.setPassword(userCreateDto.getPassword());


        return mapper.toDto(repository.save(old));
    }

    @Override
    public boolean delete(Long id) {

        repository.deleteById(id);

        UserDto delete = getById(id);

        if (Objects.isNull(delete)) {
            return true;
        } else {
            return false;
        }
    }
}
