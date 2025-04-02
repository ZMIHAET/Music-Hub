package ru.kashigin.musichub.service.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.kashigin.musichub.service.PersonService;

@Service
@Slf4j
public class DateConsumer {
    @RabbitListener(queues = "dateQueue")
    public void receiveDateMessage(DateMessage dateMessage) {
        log.info("id: {}", dateMessage.getId());
        log.info("date: {}", dateMessage.getDate());
    }
}