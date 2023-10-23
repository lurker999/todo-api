package com.mycompany.todoapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDetailsDto {
    private Long id;

    private String title;

    private Long createdAt;

    private Long updatedAt;
}
