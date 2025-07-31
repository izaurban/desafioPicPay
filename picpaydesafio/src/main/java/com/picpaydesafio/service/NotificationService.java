package com.picpaydesafio.service;

import com.picpaydesafio.domain.user.User;

public interface NotificationService {

    public void sendNotification(User user, String message) throws Exception;

}
