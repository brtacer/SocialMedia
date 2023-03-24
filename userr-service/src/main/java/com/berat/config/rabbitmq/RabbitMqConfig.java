package com.berat.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange-user}")
    private String exchangeElastic;
    @Value("${rabbitmq.registerElasticKey}")
    private String elasticBindingKey;
    @Value("${rabbitmq.queueElastic}")
    private String queueNameElastic;
    @Value("${rabbitmq.queueRegister}")
    private String queueNameRegister;


    @Bean
    DirectExchange exchangeElastic(){
        return new DirectExchange(exchangeElastic);
    }
    @Bean
    Queue registerQueue(){
        return new Queue(queueNameRegister);
    }
    @Bean
    Queue registerElasticQueue(){
        return new Queue(queueNameElastic);
    }
    @Bean
    public Binding bindingRegisterElastic(final Queue registerElasticQueue,final DirectExchange exchangeElastic){
        return BindingBuilder.bind(registerElasticQueue).to(exchangeElastic).with(elasticBindingKey);
    }



}
