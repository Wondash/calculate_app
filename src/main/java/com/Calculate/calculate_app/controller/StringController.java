package com.Calculate.calculate_app.controller;

import com.Calculate.calculate_app.converter.DoubleListConverter;
import com.Calculate.calculate_app.converter.TasksConverter;
import com.Calculate.calculate_app.dao.Methods;
import com.Calculate.calculate_app.dao.MethodsRepository;
import com.Calculate.calculate_app.dao.Tasks;
import com.Calculate.calculate_app.dao.TasksRepository;
import com.Calculate.calculate_app.dto.TasksDTO;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.rmi.common.RemoteService;
import com.Calculate.calculate_app.rmi.server.CalculationTask;
import com.Calculate.calculate_app.service.MethodsService;
import com.Calculate.calculate_app.service.TasksService;
import com.Calculate.calculate_app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
    @Autowired
    private MethodsRepository methodsRepository;
    @Autowired
    private Executor calculationExecutor;
    @Autowired
    private TasksRepository tasksRepository;

    @PostMapping("/calculate")
    public CompletableFuture<String> calculate(@RequestBody Map<String, Object> request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
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

                CalculationTask task = new CalculationTask(remoteService, method, numbers);


                TasksDTO taskDTO = new TasksDTO();
                taskDTO.setUser_id(userId);
                taskDTO.setMethod_id(methodId);
                taskDTO.setParameters(DoubleListConverter.convertListToString(numbers));
                taskDTO.setStatus(2);
//                taskDTO.setResult(Double.toString(result));
//                taskDTO.setCompleted_at(java.time.LocalDateTime.now());
                TasksDTO newTasksDTO =  TasksConverter.convertToDTO(tasksService.CreateTask(taskDTO));
                double result = task.call();

                newTasksDTO.setStatus(1);
                newTasksDTO.setResult(String.valueOf(result));
                newTasksDTO.setCompleted_at(LocalDateTime.now());

//
                tasksService.updateTask(newTasksDTO);

                return String.valueOf(result);
            } catch (RemoteException e) {
                return "发生错误: " + e.getMessage();
            }
        }, calculationExecutor);
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
//    @GetMapping("/task")
//    public ResponseEntity<?> getTasksByUserId(@RequestParam("userId") String userId) {
//        try {
//            int userIdInt = Integer.parseInt(userId);
//
//            List<TasksDTO> taskDTOList = tasksService.getTasksList(userIdInt);
//
//            if (taskDTOList == null) {
//                return ResponseEntity.ok().body(List.of()); // 返回空列表
//            }
//            return ResponseEntity.ok(taskDTOList);
//
//        } catch (NumberFormatException e) {
//            // 处理无效的用户ID（非数字参数）
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("无效的用户ID，必须为整数");
//        }
//
//    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TasksDTO>> getTasksByUserId(@RequestParam("userId") Integer userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<TasksDTO> taskList = tasksService.getTasksList(userId);
        return ResponseEntity.ok(taskList != null ? taskList : List.of());
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestParam("taskId") Integer taskId) {
        try{
            tasksService.DeleteTaskById(taskId);
            return ResponseEntity.ok("删除成功");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除任务失败");
        }
    }

    @GetMapping("/methodName")
    public ResponseEntity<String> getMethodName(@RequestParam("methodId") int methodId) {
        return methodsRepository.findById(methodId)
                .map(method -> ResponseEntity.ok(method.getName()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}