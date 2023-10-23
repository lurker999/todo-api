package com.mycompany.todoapi.service;

import com.mycompany.todoapi.model.Task;
import com.mycompany.todoapi.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<Task> findAll(Pageable pageable);
    Page<Task> searchByTitle(String title, Pageable pageable);
    Task findById(Long id);
    Task creatTask(TaskDto taskDto);
    void deleteTask(Long id);
    void updateTask(Long id, TaskDto taskDto);
}
