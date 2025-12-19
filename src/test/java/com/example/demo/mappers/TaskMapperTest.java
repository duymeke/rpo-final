package com.example.demo.mappers;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper mapper;

    @Test
    void convertEntityToDtoTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        Task entity = new Task();
        entity.setId(1L);
        entity.setTitle("Test Task");
        entity.setText("Test Description");
        entity.setCompleted(false);
        entity.setUser(user);

        TaskDto dto = mapper.toDto(entity);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertNotNull(dto.getTitle());
        Assertions.assertNotNull(dto.getText());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getTitle(), dto.getTitle());
        Assertions.assertEquals(entity.getText(), dto.getText());
        Assertions.assertEquals(entity.isCompleted(), dto.isCompleted());
        Assertions.assertEquals(user.getId(), dto.getUserId());
    }

    @Test
    void convertDtoToEntityTest() {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("Test Task");
        dto.setText("Test Description");
        dto.setCompleted(true);
        dto.setUserId(1L);

        Task entity = mapper.toEntity(dto);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getTitle());
        Assertions.assertNotNull(entity.getText());

        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getTitle(), dto.getTitle());
        Assertions.assertEquals(entity.getText(), dto.getText());
        Assertions.assertEquals(entity.isCompleted(), dto.isCompleted());
    }

    @Test
    void convertEntityListToDtoList() {
        User user = new User();
        user.setId(1L);

        List<Task> entities = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task1");
        task1.setText("Description1");
        task1.setCompleted(false);
        task1.setUser(user);
        entities.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task2");
        task2.setText("Description2");
        task2.setCompleted(true);
        task2.setUser(user);
        entities.add(task2);

        Task task3 = new Task();
        task3.setId(3L);
        task3.setTitle("Task3");
        task3.setText("Description3");
        task3.setCompleted(false);
        task3.setUser(user);
        entities.add(task3);

        List<TaskDto> dtos = mapper.toDtoList(entities);

        Assertions.assertNotNull(dtos);
        Assertions.assertNotEquals(dtos.size(), 0);
        Assertions.assertEquals(dtos.size(), entities.size());

        for (int i = 0; i < dtos.size(); i++) {
            Assertions.assertNotNull(dtos.get(i));
            Assertions.assertNotNull(dtos.get(i).getId());
            Assertions.assertNotNull(dtos.get(i).getTitle());
            Assertions.assertNotNull(dtos.get(i).getText());

            Assertions.assertEquals(dtos.get(i).getId(), entities.get(i).getId());
            Assertions.assertEquals(dtos.get(i).getTitle(), entities.get(i).getTitle());
            Assertions.assertEquals(dtos.get(i).getText(), entities.get(i).getText());
            Assertions.assertEquals(dtos.get(i).isCompleted(), entities.get(i).isCompleted());
        }
    }
}

