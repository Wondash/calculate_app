package com.Calculate.calculate_app.service;


import com.Calculate.calculate_app.converter.TasksConverter;
import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dao.TasksRepository;
import com.Calculate.calculate_app.dto.TasksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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



    @Override
    public void CreateTask(TasksDTO tasksDTO) {
//        List<Tasks> taskList = tasksRepository.findByUserIdAndMethodId(tasksDTO.getUser_id(), tasksDTO.getMethod_id());
//        if(!CollectionUtils.isEmpty(taskList)){
//            throw new IllegalStateException("重复的提交，请勿重复提交");
//        }
//        Tasks tasks = tasksRepository.save(TasksConverter.convertFromDTO(tasksDTO));
        tasksRepository.save(TasksConverter.convertFromDTO(tasksDTO));
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


}
