package com.picpaydesafio.service;

import com.picpaydesafio.domain.transaction.Transaction;
import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dto.TransactionDto;

import java.math.BigDecimal;

public interface TransactionService {

    public Transaction createTransaction(TransactionDto transactionDto) throws Exception;

    public boolean authorizeTransaction(User sender, BigDecimal value);
}
