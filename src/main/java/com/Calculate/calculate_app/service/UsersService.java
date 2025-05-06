package com.Calculate.calculate_app.service;

import com.Calculate.calculate_app.dao.Users;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.dto.UsersNoPasswordDTO;


public interface UsersService {
    UsersDTO getUserById(int id);//查
    UsersNoPasswordDTO getUserNoPasswordById(int id);
    boolean addNewUser(UsersDTO usersDTO);//增
    void deleteUserById(int id);//删
    UsersDTO updateUserById(int id,String username, String password,String email);//改
    public Users login(String username, String password);
}
