package com.Calculate.calculate_app.service;

import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dto.MethodsDTO;
import java.util.List;

public interface MethodsService {
    MethodsDTO getMethodsById(int id);
    void addNewMethod(MethodsDTO methodsDTO);
    void deleteMethodsById(int id);
    MethodsDTO updateMethodsById(MethodsDTO methodsDTO);

    List<Methods> getMethodsList();

    Methods getMethodByName(String methodName);
//    List<String> getMethodsName();
}
