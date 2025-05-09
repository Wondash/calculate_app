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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MethodsController {
    @Autowired(required = false)
    private MethodsService methodsService;

    @GetMapping("/MethodList")
    public String showMethodList(
            @RequestParam("userId") Integer userId,  // 直接从URL参数获取userId
            @RequestParam("username") String username,  // 直接从URL参数获取username
            Model model
    ) {
        // 校验参数（根据需要添加必要的校验逻辑）
        if (userId == null || username == null) {
            // 处理参数缺失情况，例如重定向到登录页
            return "redirect:/login?error=paramMissing";
        }

        // 将用户信息添加到Model，供页面使用
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);

        // 获取方法列表
        List<Methods> methodsList = methodsService.getMethodsList();
        model.addAttribute("methodsList", methodsList);

        System.out.println("收到请求：userId={"+userId+"}, username={"+username+"}");

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
