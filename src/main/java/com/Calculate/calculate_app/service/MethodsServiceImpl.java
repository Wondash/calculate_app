package com.Calculate.calculate_app.service;

import com.Calculate.calculate_app.converter.MethodsConverter;
import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dao.MethodsRepository;
import com.Calculate.calculate_app.dto.MethodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
@Service
public class MethodsServiceImpl implements MethodsService {
    @Autowired
    private MethodsRepository methodsRepository;


    @Override
    public MethodsDTO getMethodsById(int id) {
        return null;
    }

    @Override
    public void addNewMethod(MethodsDTO methodsDTO) {
        Methods methodsList = methodsRepository.findByName(methodsDTO.getName());
        if(!CollectionUtils.isEmpty((Collection<?>) methodsList)){//判空,if内的条件要改
            throw new IllegalStateException("方法已存在");
        }
        methodsRepository.save(MethodsConverter.convertMethods(methodsDTO));

    }


    @Override
    public void deleteMethodsById(int id) {

    }

    @Override
    public MethodsDTO updateMethodsById(MethodsDTO methodsDTO) {
        return null;
    }

    @Override
    public List<Methods> getMethodsList() {
        return methodsRepository.findAll();
    }

    public Methods getMethodByName(String name) {
        return methodsRepository.findByName(name);
    }
//    @Override
//    public List<String> getMethodsName() {
//        return methodsRepository.findAll();
//    }
}
