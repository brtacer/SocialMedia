package com.berat.rabbitmq.producer;

import com.berat.rabbitmq.model.RegisterMailModel;
import com.berat.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMailProducer {

    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;
    @Value("${rabbitmq.mailKey}")
    private String mailBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMail(RegisterMailModel model){
        rabbitTemplate.convertAndSend(directExchange,mailBindingKey,model);
    }

}
