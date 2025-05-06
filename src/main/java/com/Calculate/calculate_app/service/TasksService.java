package com.Calculate.calculate_app.service;

import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dto.TasksDTO;

public interface TasksService {
    //int getTaskId(int user_id, int method_id);
    void CreateTask(TasksDTO tasksDTO);
    void DeleteTaskById(int id);
    String getResultOfTaskById(int id);

}
