package com.Calculate.calculate_app.service;


import com.Calculate.calculate_app.converter.TasksConverter;
import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dao.TasksRepository;
import com.Calculate.calculate_app.dao.UsersRepository;
import com.Calculate.calculate_app.dto.TasksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Tasks CreateTask(TasksDTO tasksDTO) {
        Tasks task = new Tasks();
        task.setUser_id(tasksDTO.getUser_id());
        task.setMethod_id(tasksDTO.getMethod_id());
        task.setParameters(tasksDTO.getParameters());
        task.setStatus(2); // 初始状态：进行中
        return tasksRepository.save(task); // 保存后自动生成ID
    }

    @Override
    public void DeleteTaskById(int id){
        tasksRepository.findById(id).orElseThrow(()->new IllegalStateException("id为" + id + "的任务不存在"));
        tasksRepository.deleteById(id);
    }



    @Override
    public String getResultOfTaskById(int id) {
        Tasks task = tasksRepository.findById(id).orElseThrow(()->new IllegalStateException("id为" + id + "的任务不存在"));
        return task.getResult();
    }

    @Override
    public List<TasksDTO> getTasksList(int id) {
        List<Tasks> taskList = tasksRepository.findByUserId(id);
        if(!CollectionUtils.isEmpty(taskList)){
            return TasksConverter.convertTasksListToDTOList(taskList);
        }
        return null;
    }

    @Override
    @Transactional
    public void updateTask(TasksDTO tasksDTO) {
        Integer id = tasksDTO.getId();
        Tasks task = tasksRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setResult(tasksDTO.getResult());
        task.setCompleted_at(tasksDTO.getCompleted_at());
        task.setStatus(tasksDTO.getStatus());
        tasksRepository.save(task);
    }


}
