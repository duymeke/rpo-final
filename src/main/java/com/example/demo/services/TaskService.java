package com.example.demo.services;

import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;

import java.util.List;



public interface TaskService {


    List<TaskDto> getAll();
    TaskDto getById(Long id);
    TaskDto create(TaskDto taskDto);
    TaskDto update(Long id, TaskDto taskDto);
    boolean delete(Long id);
}
