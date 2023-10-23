package com.mycompany.todoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank(message = "title must not be blank")
    @Size(min = 4, max = 255, message = "title length needs to be between 4 and 255 characters")
    private String title;
}
