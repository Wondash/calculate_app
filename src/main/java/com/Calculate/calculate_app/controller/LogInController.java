package com.Calculate.calculate_app.controller;

import com.Calculate.calculate_app.dao.Users;
import com.Calculate.calculate_app.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogInController {
    @Autowired
    private UsersService usersService;
    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

//    @GetMapping("/login")
//    public ModelAndView showLoginPage() {
//        return new ModelAndView("login");
//    }
    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(required = false) String error,
            Model model
    ) {
        logger.info("Login Page来咯");
        if ("true".equals(error)) {
            model.addAttribute("errorMessage", "用户名或密码错误");
        } else if ("notLoggedIn".equals(error)) {
            model.addAttribute("errorMessage", "请先登录");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Users user = usersService.login(username, password);
        if (user != null) {
            // 登录成功，使用 RedirectAttributes 添加用户 ID 和用户名
            redirectAttributes.addFlashAttribute("userId", user.getId());
            redirectAttributes.addFlashAttribute("username", user.getUsername());
            return "redirect:/MethodList";
        } else {
            return "redirect:/login?error=true";
        }
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
