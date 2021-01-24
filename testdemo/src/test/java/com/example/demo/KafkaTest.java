package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class KafkaTest {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;


    @Test
    public void producerTest(){
        String message = "hello world!";
        kafkaTemplate.send( "producersToConsumers_logs", message );

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
