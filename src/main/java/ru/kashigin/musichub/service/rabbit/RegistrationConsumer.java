package ru.kashigin.musichub.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationConsumer {

    @RabbitListener(queues = "registrationQueue")
    public void receiveRegistrationMessage(String message){
        log.info("Received registration message: {}", message);
    }
}
