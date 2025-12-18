package com.example.demo.mappers;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.Category;
import com.example.demo.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    TaskDto toDto(Task task);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Task toEntity(TaskDto dto);

    List<TaskDto> toDtoList(List<Task> tasks);

}