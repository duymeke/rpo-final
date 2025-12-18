package com.example.demo.mappers;


import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { TaskMapper.class})
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserCreateDto dto);

    List<UserDto> toDtoList(List<User> users);


}
