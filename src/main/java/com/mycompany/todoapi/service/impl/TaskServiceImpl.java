package com.mycompany.todoapi.service.impl;

import com.mycompany.todoapi.exception.BusinessException;
import com.mycompany.todoapi.exception.ErrorCode;
import com.mycompany.todoapi.model.Task;
import com.mycompany.todoapi.repository.TaskRepository;
import com.mycompany.todoapi.service.TaskService;
import com.mycompany.todoapi.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final Supplier<Long> instantSupplier;

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> searchByTitle(String title, Pageable pageable) {
        return taskRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Task findById(Long id) {
        return getTask(id);
    }

    @Override
    public Task creatTask(TaskDto taskDto) {
        Task task = new Task();
        task.setCreatedAt(instantSupplier.get());
        task.setTitle(taskDto.getTitle());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTask(id);
        taskRepository.delete(task);
    }

    @Override
    public void updateTask(Long id, TaskDto taskDto) {
        Task task = getTask(id);
        task.setTitle(taskDto.getTitle());
        task.setUpdatedAt(instantSupplier.get());
        taskRepository.save(task);
    }

    private Task getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "task not found");
        }
        return task.get();
    }
}
