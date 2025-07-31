package com.picpaydesafio.service.impl;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dto.NotificationDto;
import com.picpaydesafio.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDto notificationRequest =  new NotificationDto(email, message);

    ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

    if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
        System.out.println("erro ao enviar a notificação");
        throw new Exception("Serviço de notificação fora do ar");
        }
    }
}
