package com.Calculate.calculate_app.controller;


import com.Calculate.calculate_app.Response;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.dto.UsersNoPasswordDTO;
import com.Calculate.calculate_app.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);


    @GetMapping("/user/{id}")
    public ModelAndView getUserById(@PathVariable int id) {
        UsersNoPasswordDTO user = usersService.getUserNoPasswordById(id);
        ModelAndView modelAndView = new ModelAndView("userPage");
        modelAndView.addObject("user", user);
        return modelAndView;
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
