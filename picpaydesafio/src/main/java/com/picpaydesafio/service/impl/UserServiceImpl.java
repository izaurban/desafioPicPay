package com.picpaydesafio.service.impl;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.domain.user.UserType;
import com.picpaydesafio.dto.UserDto;
import com.picpaydesafio.repository.UserRepository;
import com.picpaydesafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw  new Exception("Usuário do tipo lojista não est autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    @Override
    public User findUserById(Long id) throws Exception {
        return  this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User createUser(UserDto userDto) {
        User newUser =  new User(userDto);
        this.saveUser(newUser);
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
       return this.userRepository.findAll();}
}
