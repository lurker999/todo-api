package com.mycompany.todoapi.controller;

import com.mycompany.todoapi.model.Task;
import com.mycompany.todoapi.service.TaskService;
import com.mycompany.todoapi.dto.TaskDto;
import com.mycompany.todoapi.dto.TaskDetailsDto;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final Converter<Task, TaskDetailsDto> taskConverter;

    private final MeterRegistry registry;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TaskDetailsDto> getAllTasks(Pageable pageable, @RequestParam(required = false) String title) {
        Page<Task> taskPage;
        if (title == null) {
            log.info("Get all tasks");
            taskPage = taskService.findAll(pageable);
        } else {
            log.info("Get all tasks with title: [{}]", title);
            taskPage = taskService.searchByTitle(title, pageable);
        }
        List<TaskDetailsDto> tasks = taskPage.map(taskConverter::convert).stream().toList();
        registry.counter("get_tasks_list_count").increment();
        return new PageImpl<>(tasks, pageable, taskPage.getTotalElements());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TaskDetailsDto getById(@PathVariable Long id) {
        log.info("Get task by id: [{}]", id);
        Task task = taskService.findById(id);
        registry.counter("get_task_details_count").increment();
        return taskConverter.convert(task);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskDetailsDto creatTask(@RequestBody @Valid TaskDto taskDto) {
        log.info("Create task with title: [{}]", taskDto.getTitle());
        Task task = taskService.creatTask(taskDto);
        registry.counter("create_task_count").increment();
        return taskConverter.convert(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task with id: [{}]", id);
        taskService.deleteTask(id);
        registry.counter("delete_task_count").increment();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDto) {
        log.info("Update task with id: [{}]", id);
        taskService.updateTask(id, taskDto);
        registry.counter("update_task_count").increment();
    }

}
