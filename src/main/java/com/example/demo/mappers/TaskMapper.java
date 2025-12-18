package com.example.demo.mappers;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.Category;
import com.example.demo.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    TaskDto toDto(Task task);


    List<TaskDto> toDtoList(List<Task> tasks);

}