package com.Calculate.calculate_app.dto;

import com.Calculate.calculate_app.dao.Tasks;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TasksDTO {
    private int id;
    private int userId;
    private int methodId;
    private String parameters;
    private String result;
    private LocalDateTime completed_at;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public int getMethod_id() {
        return methodId;
    }

    public void setMethod_id(int method_id) {
        this.methodId = method_id;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(LocalDateTime completed_at) {
        this.completed_at = completed_at;
    }
}
