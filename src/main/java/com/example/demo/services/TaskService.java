package com.example.demo.services;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;

import java.util.List;



public interface TaskService {


    List<TaskDto> getAll(Long userId);
    TaskDto getById(Long id, Long userId);
    TaskDto create(TaskDto taskDto);
    TaskDto update(Long id, TaskDto taskDto, Long userId);
    boolean delete(Long id, Long userId);
}
