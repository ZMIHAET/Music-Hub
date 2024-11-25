package ru.kashigin.musichub.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue registrationQueue(){
        return new Queue("registrationQueue", false);
    }
}