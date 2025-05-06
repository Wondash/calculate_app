package com.Calculate.calculate_app.rmi.client;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import com.Calculate.calculate_app.service.MethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;

@Controller
public class RmiClientController {
    @Autowired
    private RemoteService remoteService;//远程服务
    @Autowired
    private MethodsService methodsService;//计算方法服务

    @GetMapping("/hello")
    public String sayHello() {//测试使用
        try {
            return remoteService.sayHello();
        } catch (RemoteException e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/calculate")
    public String showCalculatePage(@RequestParam("userId") Integer userId,
                                    @RequestParam("username") String username,
                                    Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        return "Calculate";
    }



}