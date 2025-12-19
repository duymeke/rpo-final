package com.example.demo.services;

import com.example.demo.dto.TaskDto;
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
public class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Autowired
    private UserService userService;

    @Test
    void getAllTest() {

        List<UserDto> users = userService.getAll();
        if (users.isEmpty()) {

            UserCreateDto userDto = new UserCreateDto();
            userDto.setUsername("testuser");
            userDto.setEmail("testuser" + System.currentTimeMillis() + "@test.com");
            userDto.setPassword("password123");
            UserDto createdUser = userService.create(userDto);
            Long userId = createdUser.getId();

            List<TaskDto> dtos = service.getAll(userId);

            Assertions.assertNotNull(dtos);
        } else {
            Long userId = users.get(0).getId();
            List<TaskDto> dtos = service.getAll(userId);

            Assertions.assertNotNull(dtos);

            for (TaskDto dto : dtos) {
                Assertions.assertNotNull(dto.getId());
                Assertions.assertNotNull(dto.getTitle());
            }
        }
    }

    @Test
    void getByIdTest() {
        List<UserDto> users = userService.getAll();
        if (users.isEmpty()) {
            return;
        }

        Long userId = users.get(0).getId();
        List<TaskDto> allTasks = service.getAll(userId);

        if (allTasks.isEmpty()) {
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allTasks.size());
        Long someIndex = allTasks.get(randomIndex).getId();

        TaskDto dto = service.getById(someIndex, userId);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getTitle());
    }

    @Test
    void addTest() {

        List<UserDto> users = userService.getAll();
        Long userId;
        if (users.isEmpty()) {
            UserCreateDto userDto = new UserCreateDto();
            userDto.setUsername("testuser");
            userDto.setEmail("testuser" + System.currentTimeMillis() + "@test.com");
            userDto.setPassword("password123");
            UserDto createdUser = userService.create(userDto);
            userId = createdUser.getId();
        } else {
            userId = users.get(0).getId();
        }

        int before = service.getAll(userId).size();

        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test-Task");
        taskDto.setText("Test Description");
        taskDto.setCompleted(false);
        taskDto.setUserId(userId);

        TaskDto saved = service.create(taskDto);

        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getTitle());
        Assertions.assertNotNull(saved.getText());

        TaskDto savedTest = service.getById(saved.getId(), userId);

        Assertions.assertNotNull(savedTest);
        Assertions.assertNotNull(savedTest.getId());
        Assertions.assertNotNull(savedTest.getTitle());
        Assertions.assertNotNull(savedTest.getText());

        Assertions.assertEquals(saved.getId(), savedTest.getId());
        Assertions.assertEquals(saved.getTitle(), savedTest.getTitle());
        Assertions.assertEquals(saved.getText(), savedTest.getText());

        int after = service.getAll(userId).size();
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    void updateTest() {
        List<UserDto> users = userService.getAll();
        if (users.isEmpty()) {
            return;
        }

        Long userId = users.get(0).getId();
        List<TaskDto> allTasks = service.getAll(userId);

        if (allTasks.isEmpty()) {
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allTasks.size());
        Long someIndex = allTasks.get(randomIndex).getId();

        TaskDto newTask = new TaskDto();
        newTask.setId(someIndex);
        newTask.setTitle("TestUpdate");
        newTask.setText("Updated Description");
        newTask.setCompleted(true);
        newTask.setUserId(userId);

        TaskDto updated = service.update(someIndex, newTask, userId);

        Assertions.assertNotNull(updated);
        Assertions.assertNotNull(updated.getId());
        Assertions.assertNotNull(updated.getTitle());
        Assertions.assertNotNull(updated.getText());

        TaskDto updateTest = service.getById(someIndex, userId);

        Assertions.assertNotNull(updateTest);
        Assertions.assertNotNull(updateTest.getId());
        Assertions.assertNotNull(updateTest.getTitle());
        Assertions.assertNotNull(updateTest.getText());

        Assertions.assertEquals(updated.getId(), updateTest.getId());
        Assertions.assertEquals(updated.getTitle(), updateTest.getTitle());
        Assertions.assertEquals(updated.getText(), updateTest.getText());
        Assertions.assertEquals(updated.isCompleted(), updateTest.isCompleted());
    }

    @Test
    void deleteTest() {
        List<UserDto> users = userService.getAll();
        if (users.isEmpty()) {
            return;
        }

        Long userId = users.get(0).getId();
        int before = service.getAll(userId).size();

        if (before == 0) {
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(service.getAll(userId).size());
        Long someIndex = service.getAll(userId).get(randomIndex).getId();

        boolean deleted = service.delete(someIndex, userId);
        Assertions.assertTrue(deleted);

        TaskDto deletedTest = service.getById(someIndex, userId);
        Assertions.assertNull(deletedTest);

        int after = service.getAll(userId).size();
        Assertions.assertEquals(before - 1, after);
    }
}

