package com.picpaydesafio.service;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dto.UserDto;


import java.math.BigDecimal;
import java.util.List;


public interface UserService{

    public void validateTransaction(User sender, BigDecimal amount) throws Exception;

    public User findUserById(Long id) throws Exception;

    public void saveUser(User user);

    public  User createUser(UserDto userDto);

    public List<User> getAllUsers();
}
