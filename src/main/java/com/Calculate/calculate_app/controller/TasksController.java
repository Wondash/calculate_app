package com.Calculate.calculate_app.controller;

import com.Calculate.calculate_app.dto.TasksDTO;
import com.Calculate.calculate_app.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TasksController {
    @Autowired
    private TasksService tasksService;



    @PostMapping("/createTask")
    public void createTask(@RequestBody TasksDTO tasksDTO) {
        tasksService.CreateTask(tasksDTO);
    }
}
