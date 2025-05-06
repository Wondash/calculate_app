package com.Calculate.calculate_app.converter;

import com.Calculate.calculate_app.dao.Users;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.dto.UsersNoPasswordDTO;

import java.util.List;

public class UsersConverter {

    public static UsersDTO convertUsers(Users users) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setId(users.getId());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setEmail(users.getEmail());
        return usersDTO;
    }

    public static Users convertUsers(UsersDTO usersDTO) {
        Users users = new Users();
        users.setId(usersDTO.getId());
        users.setUsername(usersDTO.getUsername());
        users.setPassword(usersDTO.getPassword());

        users.setEmail(usersDTO.getEmail());
        return users;
    }
    public static UsersNoPasswordDTO convertUsersNoPassword(Users users) {//只显示信息，不显示密码
        UsersNoPasswordDTO usersNoPasswordDTO = new UsersNoPasswordDTO();
        usersNoPasswordDTO.setId(users.getId());
        usersNoPasswordDTO.setUsername(users.getUsername());
        //usersNoPasswordDTO.setPassword(users.getPassword());
        usersNoPasswordDTO.setEmail(users.getEmail());
        return usersNoPasswordDTO;
    }



}
