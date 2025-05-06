package com.Calculate.calculate_app.converter;

import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dto.MethodsDTO;

public class MethodsConverter {

    public static MethodsDTO convertMethods(Methods methods){
        MethodsDTO methodsDTO = new MethodsDTO();
        methodsDTO.setId(methods.getId());
        methodsDTO.setName(methods.getName());
        methodsDTO.setDescription(methods.getDescription());
        methodsDTO.setParameters(methods.getParameters());
        return methodsDTO;
    }

    public static Methods convertMethods(MethodsDTO methodsDTO){
        Methods methods = new Methods();
        methods.setId(methodsDTO.getId());
        methods.setName(methodsDTO.getName());
        methods.setDescription(methodsDTO.getDescription());
        methods.setParameters(methodsDTO.getParameters());
        return methods;
    }
}
