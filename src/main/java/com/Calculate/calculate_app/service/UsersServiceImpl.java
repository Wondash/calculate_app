package com.Calculate.calculate_app.service;

import com.Calculate.calculate_app.converter.UsersConverter;
import com.Calculate.calculate_app.dao.Users;
import com.Calculate.calculate_app.dao.UsersRepository;
import com.Calculate.calculate_app.dto.UsersDTO;
import com.Calculate.calculate_app.dto.UsersNoPasswordDTO;
import com.Calculate.calculate_app.tools.PasswordEncryptor;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UsersServiceImpl  implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    //private BCryptPasswordEncoder passwordEndcoder = new BCryptPasswordEncoder();

    @Override
    public UsersDTO getUserById(int id){
        Users users = usersRepository.findById(id).orElseThrow(RuntimeException::new);
        return UsersConverter.convertUsers(users);
    }

    @Override
    public UsersNoPasswordDTO getUserNoPasswordById(int id) {
        Users usersNoPasswordDTO = usersRepository.findById(id).orElseThrow(RuntimeException::new);
        return UsersConverter.convertUsersNoPassword(usersNoPasswordDTO);
    }

    @Override
    public Users login(String username, String password) {
        Users user = usersRepository.findByUsername(username)
                .stream()
                .findFirst() //
                .orElse(null); // 避免空列表处理
        if (user == null) {
            throw new IllegalStateException("用户不存在"); // 用户不存在
        }
        if (Objects.equals(PasswordEncryptor.encryptPassword(password), user.getPassword())) {//判断密码是否正确
            return user;
        }else{
            return null;
        }
    }

    @Override
    public Optional<Users> findById(int id) {
        return usersRepository.findById(id);
    }

    @Override
    public void updateUsernameById(int id, String username) {
        Users usersInDB = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));
        if(!StringUtils.isEmpty(username) && !usersInDB.getUsername().equals(username)){
            usersInDB.setUsername(username);
        }
        Users users = usersRepository.save(usersInDB);//保存更改
        UsersConverter.convertUsers(users);
    }

    @Override
    public boolean updatePasswordById(int id, String oldPassword, String newPassword) {
        Users usersInDB = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));
        if(!StringUtils.isEmpty(usersInDB.getPassword())&&!oldPassword.equals(usersInDB.getPassword())){
            usersInDB.setPassword(PasswordEncryptor.encryptPassword(newPassword));
        }else{
            return false;
        }
        usersRepository.save(usersInDB);//保存更改
        return true;
    }

    @Override
    public void updateEmailById(int id, String email) {
        Users usersInDB = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));
        if(!StringUtils.isEmpty(email) && !usersInDB.getEmail().equals(email)){
            usersInDB.setEmail(email);
        }
        Users users = usersRepository.save(usersInDB);//保存更改
        UsersConverter.convertUsers(users);
    }

    @Override
    public void updateUserNoPasswordById(int id, String username, String email) {
        Users usersInDB = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));

        //更新用户名
        if(!StringUtils.isEmpty(username) && !usersInDB.getUsername().equals(username)){
            usersInDB.setUsername(username);
        }
        //更新邮箱
        if(!StringUtils.isEmpty(email) && !usersInDB.getEmail().equals(email)){
            usersInDB.setEmail(email);
        }

        usersRepository.save(usersInDB);//保存更改
    }

    @Override
    public boolean addNewUser(UsersDTO usersDTO) {
        Optional<Users> usersList = usersRepository.findByUsername(usersDTO.getUsername());
        if(usersList.isPresent()) {
            return false;
        }
        //Users users = usersRepository.save(UsersConverter.convertUsers(usersDTO));
        Users users = UsersConverter.convertUsers(usersDTO);
        users.setPassword(PasswordEncryptor.encryptPassword(usersDTO.getPassword()));
        usersRepository.save(users);
        return true;
    }

    @Override
    public void deleteUserById(int id) {
        usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));
        usersRepository.deleteById(id);

    }

    @Override
    @Transactional//操作失败则回滚
    public UsersDTO updateUserById(int id,String username, String password,String email) {
        Users usersInDB = usersRepository.findById(id).orElseThrow(()->new IllegalStateException("id:" + id + "不存在"));

        //更新密码
        if(/*!passwordEndcoder.matches(password,usersInDB.getPassword()) && */!StringUtils.isEmpty(usersInDB.getPassword())){
            usersInDB.setPassword(PasswordEncryptor.encryptPassword(password));
        }
        //更新用户名
        if(!StringUtils.isEmpty(username) && !usersInDB.getUsername().equals(username)){
            usersInDB.setUsername(username);
        }
        //更新邮箱
        if(!StringUtils.isEmpty(email) && !usersInDB.getEmail().equals(email)){
            usersInDB.setEmail(email);
        }

        Users users = usersRepository.save(usersInDB);//保存更改
        return UsersConverter.convertUsers(users);



    }
}
