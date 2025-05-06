package com.Calculate.calculate_app.converter;

import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dto.TasksDTO;

public class TasksConverter {

    public static TasksDTO convertTasks(Tasks tasks) {
        TasksDTO tasksDTO = new TasksDTO();
        tasksDTO.setId(tasks.getId());
        tasksDTO.setUser_id(tasks.getUser_id());
        tasksDTO.setMethod_id(tasks.getMethod_id());
        tasksDTO.setParameters(tasks.getParameters());
        tasksDTO.setResult(tasks.getResult());
        tasksDTO.setCompleted_at(tasks.getCompleted_at());
        return tasksDTO;
    }

    public static Tasks convertTasks(TasksDTO tasksDTO) {
        Tasks tasks = new Tasks();
        tasks.setId(tasksDTO.getId());
        tasks.setUser_id(tasksDTO.getUser_id());
        tasks.setMethod_id(tasksDTO.getMethod_id());
        tasks.setParameters(tasksDTO.getParameters());
        tasks.setResult(tasksDTO.getResult());
        tasks.setCompleted_at(tasksDTO.getCompleted_at());
        return tasks;
    }
}
