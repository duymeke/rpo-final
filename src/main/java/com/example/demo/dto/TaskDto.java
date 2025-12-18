package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String text;
    private boolean completed;
    private Long userId;
    private List<CategoryDto> categories;
}
