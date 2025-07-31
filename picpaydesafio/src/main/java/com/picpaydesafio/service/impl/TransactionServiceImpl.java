package com.picpaydesafio.service.impl;

import com.picpaydesafio.domain.transaction.Transaction;
import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dto.TransactionDto;
import com.picpaydesafio.repository.TransactionRepository;
import com.picpaydesafio.service.NotificationService;
import com.picpaydesafio.service.TransactionService;
import com.picpaydesafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NotificationService notificationService;

    @Override
    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User sender = this.userService.findUserById(transactionDto.senderId());
        User receiver = this.userService.findUserById(transactionDto.receiverId());

        userService.validateTransaction(sender, transactionDto.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDto.value());
        if(!this.authorizeTransaction(sender, transactionDto.value())){
            throw new Exception("Transação não autorizada");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return new Transaction();

    }

    @Override
    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
