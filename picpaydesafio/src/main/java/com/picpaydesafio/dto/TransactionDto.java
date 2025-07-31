package com.picpaydesafio.dto;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value, Long senderId, Long receiverId) {
}
