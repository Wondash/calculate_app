package com.Calculate.calculate_app.controller;

import com.Calculate.calculate_app.converter.DoubleListConverter;
import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dao.MethodsRepository;
import com.Calculate.calculate_app.dto.TasksDTO;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.rmi.common.RemoteService;
import com.Calculate.calculate_app.service.MethodsService;
import com.Calculate.calculate_app.service.TasksService;
import com.Calculate.calculate_app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StringController {

    @Autowired
    private RemoteService remoteService;
    @Autowired
    private MethodsService methodsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private TasksService tasksService;

//    @PostMapping("/calculate")//计算页面
//    public String calculate(@RequestBody Map<String, Object> request) {
//        String method = (String) request.get("method");
//        List<Object> inputNumbers = (List<Object>) request.get("numbers");
//        List<Double> numbers = new ArrayList<>();
//        for (Object num : inputNumbers) {
//            if (num instanceof Integer) {
//                numbers.add(((Integer) num).doubleValue());
//            } else if (num instanceof Double) {
//                numbers.add((Double) num);
//            } else {
//                return "输入参数类型错误，必须是数字";
//            }
//        }
//        try {
//            double result = remoteService.calculate(method, numbers);
//            return String.valueOf(result);
//        } catch (RemoteException e) {
//            return "发生错误: " + e.getMessage();
//        }
//    }
@PostMapping("/calculate")
public String calculate(@RequestBody Map<String, Object> request) {
    String userIdStr = (String) request.get("userId");
    Integer userId = Integer.parseInt(userIdStr);

    String methodIdStr = (String) request.get("methodId");
    Integer methodId = Integer.parseInt(methodIdStr);

    String method = (String) request.get("method");
    List<Object> inputNumbers = (List<Object>) request.get("numbers");
    List<Double> numbers = new ArrayList<>();
    for (Object num : inputNumbers) {
        if (num instanceof Integer) {
            numbers.add(((Integer) num).doubleValue());
        } else if (num instanceof Double) {
            numbers.add((Double) num);
        } else {
            return "输入参数类型错误，必须是数字";
        }
    }

    try {
        double result = remoteService.calculate(method, numbers);

        TasksDTO task = new TasksDTO();
        task.setUser_id(userId);
        task.setMethod_id(methodId);
        task.setParameters(DoubleListConverter.convertListToString(numbers));
        task.setResult(Double.toString(result));
        task.setCompleted_at(java.time.LocalDateTime.now());
        tasksService.CreateTask(task); // 调用服务层方法

        return String.valueOf(result); // 保留原有结果返回
    } catch (RemoteException e) {
        return "发生错误: " + e.getMessage(); // 保留原有错误处理
    }
}
    @PostMapping("/register")
    public String addNewUser(@RequestBody UsersDTO usersDTO) {
        if (usersService.addNewUser(usersDTO)) {
            // 重定向时携带用户名参数（URL编码处理）
            String encodedUsername = URLEncoder.encode(usersDTO.getUsername(), StandardCharsets.UTF_8);
            return "redirect:/login?username=" + encodedUsername;
        } else {
            return "用户名已存在，请重新输入";
        }
    }

    @PostMapping("/calculateWithFile")
    public String calculateWithFile(
            @RequestParam("method") String method,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        // 解析文件内容为参数列表
        List<Double> numbers = parseFile(file.getInputStream());

        // 调用计算逻辑
        try {
            double result = remoteService.calculate(method, numbers);
            return String.valueOf(result);
        } catch (RemoteException e) {
            return "发生错误: " + e.getMessage();
        }
    }

    private List<Double> parseFile(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    @GetMapping("/getMethods")
    public List<Methods> getMethods() {
        return methodsService.getMethodsList();
    }

    @GetMapping("/getMethodDetails")
    public Map<String, String> getMethodDetails(@RequestParam("methodName") String methodName) {
        Methods method = methodsService.getMethodByName(methodName);
        Map<String, String> details = new HashMap<>();
        if (method != null) {
            details.put("methodId", String.valueOf(method.getId()));
            details.put("description", method.getDescription());
            details.put("parameters", method.getParameters());
        }
        return details;
    }
}