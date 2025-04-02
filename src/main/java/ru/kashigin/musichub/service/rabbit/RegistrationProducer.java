package ru.kashigin.musichub.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendRegistrationMessage(Message message){
        rabbitTemplate.convertAndSend("registrationQueue", message);
    }
}