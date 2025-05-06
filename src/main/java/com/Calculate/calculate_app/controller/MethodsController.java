package com.Calculate.calculate_app.controller;

import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dto.MethodsDTO;
import com.Calculate.calculate_app.service.MethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MethodsController {
    @Autowired(required = false)
    private MethodsService methodsService;

    @GetMapping("/MethodList")
    public String showMethodList(Model model) {
        // 从 Model 中获取用户 ID 和用户名
        Integer userId = (Integer) model.asMap().get("userId");
        String username = (String) model.asMap().get("username");

        if (userId != null && username != null) {
            model.addAttribute("userId", userId);
            model.addAttribute("username", username);
        }

        List<Methods> methodsList = methodsService.getMethodsList();
        model.addAttribute("methodsList", methodsList);
        return "MethodList";
    }

    @GetMapping("/Calculate_List")
    public List<Methods> GetMethodsList() {
        return methodsService.getMethodsList();
    }

    @PostMapping("/addNewMethods")
    public String addNewMethods(@RequestBody MethodsDTO methodsDTO) {
        methodsService.addNewMethod(methodsDTO);
        return "OK";
    }

}
