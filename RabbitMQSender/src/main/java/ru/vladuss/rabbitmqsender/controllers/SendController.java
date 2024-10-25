package ru.vladuss.rabbitmqsender.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class SendController {
    private final RabbitTemplate rabbitTemplate;
    private static Long counter = 0L;
    static final String exchangeName = "testExchange";

    @Autowired
    public SendController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/message")
    public ResponseEntity<String> sendMessageToQueue1(@RequestBody String message) {
        rabbitTemplate.convertAndSend(exchangeName, "my.key",message);

        return ResponseEntity.ok().body("Send to Queue1");
    }
}