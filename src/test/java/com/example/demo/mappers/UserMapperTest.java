package com.example.demo.mappers;

import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    @Test
    void convertEntityToDtoTest() {
        User entity = new User();
        entity.setId(1L);
        entity.setUsername("testuser");
        entity.setEmail("test@example.com");

        UserDto dto = mapper.toDto(entity);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getUsername());
        Assertions.assertNotNull(dto.getEmail());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getUsername(), dto.getUsername());
        Assertions.assertEquals(entity.getEmail(), dto.getEmail());
    }

    @Test
    void convertDtoToEntityTest() {
        UserCreateDto dto = new UserCreateDto();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("password123");

        User entity = mapper.toEntity(dto);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getUsername());
        Assertions.assertNotNull(entity.getEmail());

        Assertions.assertEquals(entity.getUsername(), dto.getEmail());
        Assertions.assertEquals(entity.getEmail(), dto.getEmail());
    }

    @Test
    void convertEntityListToDtoList() {
        List<User> entities = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        entities.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        entities.add(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("user3");
        user3.setEmail("user3@example.com");
        entities.add(user3);

        List<UserDto> dtos = mapper.toDtoList(entities);

        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(dtos.size(), 0);
        Assertions.assertEquals(dtos.size(), entities.size());

        for (int i = 0; i < dtos.size(); i++) {
            Assertions.assertNotNull(dtos.get(i));
            Assertions.assertNotNull(dtos.get(i).getId());
            Assertions.assertNotNull(dtos.get(i).getUsername());
            Assertions.assertNotNull(dtos.get(i).getEmail());

            Assertions.assertEquals(dtos.get(i).getId(), entities.get(i).getId());
            Assertions.assertEquals(dtos.get(i).getUsername(), entities.get(i).getUsername());
            Assertions.assertEquals(dtos.get(i).getEmail(), entities.get(i).getEmail());
        }
    }
}

