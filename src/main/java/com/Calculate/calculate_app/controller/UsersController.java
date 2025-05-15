package com.Calculate.calculate_app.controller;


import com.Calculate.calculate_app.Response;
import com.Calculate.calculate_app.dao.Users;
import com.Calculate.calculate_app.dao.UsersRepository;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.dto.UsersNoPasswordDTO;
import com.Calculate.calculate_app.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UsersRepository usersRepository;


    @GetMapping("/userInfo")
    public String showUserInfo(
            @RequestParam(value = "userId", required = false) Integer userId, // 参数可选
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "用户ID不能为空");
            return "redirect:/login";
        }

        Optional<Users> userOptional = usersService.findById(userId);

        if (userOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "用户不存在");
            return "redirect:/login";
        }

        model.addAttribute("user", userOptional.get());
        return "userPage";
    }
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(
            @RequestParam("userId") Integer userId, // 使用包装类型处理空值
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            RedirectAttributes redirectAttributes) {

        try {
            UsersDTO user = usersService.getUserById(userId); // 先查询用户
            usersService.updateUserNoPasswordById(userId, username, email);

        } catch (Exception e) {
            // 记录详细错误日志（生产环境建议使用日志框架）
            System.err.println("更新用户信息失败：" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "更新失败：请联系管理员");
        }
        return "redirect:/userInfo?userId=" + userId;
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("userId") int userId,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) {
        try {
            usersService.updatePasswordById(userId, oldPassword, newPassword);
            redirectAttributes.addFlashAttribute("successMsg", "密码更新成功");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "密码更新失败: " + e.getMessage());
        }

        return "redirect:/userInfo?userId=" + userId;
    }
//    @PostMapping("/updateUsername")
//    public String updateUsername(@RequestParam("userId") int userId,
//                                 @RequestParam("newUsername") String newUsername) {
//        usersService.updateUsernameById(userId, newUsername);
//        return "redirect:/userInfo?userId=" + userId; // 修改为重定向到/userInfo?userId=...
//    }
//
//    @PostMapping("/updateEmail")
//    public String updateEmail(@RequestParam("userId") int userId,
//                              @RequestParam("newEmail") String newEmail) {
//        usersService.updateEmailById(userId, newEmail);
//        return "redirect:/userInfo?userId=" + userId; // 修改为重定向到/userInfo?userId=...
//    }
//
//    @PostMapping("/updatePassword")
//    public String updatePassword(@RequestParam("userId") int userId,
//                                 @RequestParam("oldPassword") String oldPassword,
//                                 @RequestParam("newPassword") String newPassword) {
//        usersService.updatePasswordById(userId, oldPassword, newPassword);
//        return "redirect:/userInfo?userId=" + userId; // 修改为重定向到/userInfo?userId=...
//    }

    @GetMapping("/updateUserInfo")
    public String showUpdateUserInfoPage(@RequestParam("userId") int userId, Model model) {
        UsersDTO user = usersService.getUserById(userId);
        if (user == null) {
            return "redirect:/error?msg=用户不存在";
        }
        model.addAttribute("user", user);
        return "updateUserInfo"; // 返回修改用户信息的视图名称
    }

    @GetMapping("/updatePassword")
    public String showUpdatePasswordPage(@RequestParam("userId") int userId, Model model) {
        UsersDTO user = usersService.getUserById(userId);
        if (user == null) {
            return "redirect:/error?msg=用户不存在";
        }
        model.addAttribute("user", user);
        return "updatePassword"; // 返回修改密码的视图名称
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }





    @DeleteMapping("/delete/{id}")//待更改
    public void deleteUserById(@PathVariable int id) {
        usersService.deleteUserById(id);
    }

    @PutMapping("/update/{id}")//待更改
    public  Response<UsersDTO> updateUserById(@PathVariable int id, @RequestParam(required = false) String username,
                                              @RequestParam(required = false) String password,
                                              @RequestParam(required = false) String email) {
        return Response.newSuccess(usersService.updateUserById(id,username,password,email));
    }


}
