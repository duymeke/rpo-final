package com.example.demo.services;

import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void getAllTest() {
        List<UserDto> dtos = service.getAll();

        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(0, dtos.size());

        for (UserDto dto : dtos) {
            Assertions.assertNotNull(dto.getId());
            Assertions.assertNotNull(dto.getUsername());
            Assertions.assertNotNull(dto.getEmail());
        }
    }

    @Test
    void getByIdTest() {
        Random random = new Random();
        int randomIndex = random.nextInt(service.getAll().size());
        Long someIndex = service.getAll().get(randomIndex).getId();

        UserDto dto = service.getById(someIndex);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getUsername());
        Assertions.assertNotNull(dto.getEmail());
    }

    @Test
    void addTest() {
        int before = service.getAll().size();

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("Test-User");
        userCreateDto.setEmail("testuser" + System.currentTimeMillis() + "@test.com");
        userCreateDto.setPassword("password123");

        UserDto saved = service.create(userCreateDto);

        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getUsername());
        Assertions.assertNotNull(saved.getEmail());

        UserDto savedTest = service.getById(saved.getId());

        Assertions.assertNotNull(savedTest);
        Assertions.assertNotNull(savedTest.getId());
        Assertions.assertNotNull(savedTest.getUsername());
        Assertions.assertNotNull(savedTest.getEmail());

        Assertions.assertEquals(saved.getId(), savedTest.getId());
        Assertions.assertEquals(saved.getUsername(), savedTest.getUsername());
        Assertions.assertEquals(saved.getEmail(), savedTest.getEmail());

        int after = service.getAll().size();
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    void updateTest() {
        Random random = new Random();
        int randomIndex = random.nextInt(service.getAll().size());
        Long someIndex = service.getAll().get(randomIndex).getId();

        UserCreateDto newUser = new UserCreateDto();
        newUser.setUsername("TestUpdate");
        newUser.setEmail("update" + System.currentTimeMillis() + "@test.com");
        newUser.setPassword("newpassword123");

        UserDto updated = service.update(someIndex, newUser);

        Assertions.assertNotNull(updated);
        Assertions.assertNotNull(updated.getId());
        Assertions.assertNotNull(updated.getUsername());
        Assertions.assertNotNull(updated.getEmail());

        UserDto updateTest = service.getById(someIndex);

        Assertions.assertNotNull(updateTest);
        Assertions.assertNotNull(updateTest.getId());
        Assertions.assertNotNull(updateTest.getUsername());
        Assertions.assertNotNull(updateTest.getEmail());

        Assertions.assertEquals(updated.getId(), updateTest.getId());
        Assertions.assertEquals(updated.getUsername(), updateTest.getUsername());
    }

    @Test
    void deleteTest() {
        int before = service.getAll().size();

        Random random = new Random();
        int randomIndex = random.nextInt(service.getAll().size());
        Long someIndex = service.getAll().get(randomIndex).getId();

        boolean deleted = service.delete(someIndex);
        Assertions.assertTrue(deleted);

        UserDto deletedTest = service.getById(someIndex);
        Assertions.assertNull(deletedTest);

        int after = service.getAll().size();
        Assertions.assertEquals(before - 1, after);
    }
}

