package com.Calculate.calculate_app.converter;

import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dto.TasksDTO;

import java.util.ArrayList;
import java.util.List;

public class TasksConverter {

    public static TasksDTO convertToDTO(Tasks tasks) {
        TasksDTO tasksDTO = new TasksDTO();
        tasksDTO.setId(tasks.getId());
        tasksDTO.setUser_id(tasks.getUser_id());
        tasksDTO.setMethod_id(tasks.getMethod_id());
        tasksDTO.setParameters(tasks.getParameters());
        tasksDTO.setResult(tasks.getResult());
        tasksDTO.setCompleted_at(tasks.getCompleted_at());
        tasksDTO.setStatus(tasks.getStatus());
        return tasksDTO;
    }

    public static Tasks convertFromDTO(TasksDTO tasksDTO) {
        Tasks tasks = new Tasks();
        tasks.setId(tasksDTO.getId());
        tasks.setUser_id(tasksDTO.getUser_id());
        tasks.setMethod_id(tasksDTO.getMethod_id());
        tasks.setParameters(tasksDTO.getParameters());
        tasks.setResult(tasksDTO.getResult());
        tasks.setCompleted_at(tasksDTO.getCompleted_at());
        tasks.setStatus(tasksDTO.getStatus());
        return tasks;
    }

    public static List<Tasks> convertDTOListToTasksList(List<TasksDTO> tasksDTOList) {
        List<Tasks> tasksList = new ArrayList<>();
        for (TasksDTO tasksDTO : tasksDTOList) {
            Tasks tasks = convertFromDTO(tasksDTO);
            tasksList.add(tasks);
        }
        return tasksList;
    }

    public static List<TasksDTO> convertTasksListToDTOList(List<Tasks> tasksList) {
        List<TasksDTO> tasksDTOList = new ArrayList<>();
        for (Tasks tasks : tasksList) {
            TasksDTO tasksDTO = convertToDTO(tasks);
            tasksDTOList.add(tasksDTO);
        }
        return tasksDTOList;
    }
}
