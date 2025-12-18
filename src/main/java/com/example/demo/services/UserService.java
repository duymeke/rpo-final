package com.example.demo.services;


import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(Long id, UserCreateDto userCreateDto);
    boolean delete(Long id);


}
