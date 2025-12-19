package com.example.demo.services.impl;


import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.mappers.TaskMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper mapper;


    @Override
    public List<TaskDto> getAll(Long userId) {
        if (userId == null) {
            return List.of();
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return List.of();
        }
        return mapper.toDtoList(repository.findByUser(user));
    }

    @Override
    public TaskDto getById(Long id, Long userId) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            return null;
        }

        if (userId != null && task.getUser() != null && !task.getUser().getId().equals(userId)) {
            return null;
        }
        return mapper.toDto(task);
    }

    @Override
    public TaskDto create(TaskDto dto) {
        if (dto == null) return null;

        Task task = mapper.toEntity(dto);


        User user = userRepository.findById(dto.getUserId()).orElse(null);
        task.setUser(user);

        if (dto.getCategories() != null) {
            task.setCategories(
                    new ArrayList<>(
                            dto.getCategories().stream()
                                    .map(CategoryDto::getId)
                                    .map(categoryRepository::findById)
                                    .flatMap(java.util.Optional::stream)
                                    .toList()
                    )
            );
        }


        return mapper.toDto(repository.save(task));
    }

    @Override
    public TaskDto update(Long id, TaskDto dto, Long userId) {
        Task task = repository.findById(id).orElse(null);
        if (task == null || dto == null) return null;

        if (userId != null && task.getUser() != null && !task.getUser().getId().equals(userId)) {
            return null;
        }

        task.setTitle(dto.getTitle());
        task.setText(dto.getText());
        task.setCompleted(dto.isCompleted());

        if (dto.getCategories() != null) {
            task.setCategories(
                    new ArrayList<>(
                            dto.getCategories().stream()
                                    .map(CategoryDto::getId)
                                    .map(categoryRepository::findById)
                                    .flatMap(java.util.Optional::stream)
                                    .toList()
                    )
            );
        }


        return mapper.toDto(repository.save(task));
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Task task = repository.findById(id).orElse(null);
        if (task == null) {
            return false;
        }

        if (userId != null && task.getUser() != null && !task.getUser().getId().equals(userId)) {
            return false;
        }

        repository.deleteById(id);

        TaskDto delete = getById(id, userId);

        if (Objects.isNull(delete)) {
            return true;
        } else {
            return false;
        }
    }
}
