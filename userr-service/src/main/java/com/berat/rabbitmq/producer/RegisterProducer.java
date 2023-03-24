package com.berat.rabbitmq.producer;

import com.berat.rabbitmq.model.RegisterElasticModel;
import com.berat.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProducer {

    @Value("${rabbitmq.exchange-user}")
    private String exchangeElastic;
    @Value("${rabbitmq.registerElasticKey}")
    private String elasticBindingKey;
    private final RabbitTemplate rabbitTemplate;

    public void sendNewUser(RegisterElasticModel model){
        rabbitTemplate.convertAndSend(exchangeElastic,elasticBindingKey,model);
    }

}
