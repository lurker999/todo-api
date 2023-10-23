package com.mycompany.todoapi.dto.converter;

import com.mycompany.todoapi.dto.TaskDetailsDto;
import com.mycompany.todoapi.model.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<Task, TaskDetailsDto> {

    public TaskDetailsDto convert(Task source) {
        return TaskDetailsDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }

}
