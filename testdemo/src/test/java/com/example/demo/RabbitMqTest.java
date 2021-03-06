package com.example.demo;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RabbitMqTest {

    @Test
    public void producerTest() {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("guest");
            factory.setPassword("guest");
            //设置 RabbitMQ 地址
            factory.setHost("localhost");
            //建立到代理服务器到连接
            Connection conn = factory.newConnection();
            //获得信道
            Channel channel = conn.createChannel();
            //声明交换器
            String exchangeName = "hello-exchange";
            channel.exchangeDeclare(exchangeName, "direct", true);

            String routingKey = "hola";
            //发布消息
            byte[] messageBodyBytes = "quit".getBytes();
            channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

            channel.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Exception happen!");
        }
    }

    @Test
    public void consumerTest() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setHost("localhost");
            //建立到代理服务器到连接
            Connection conn = factory.newConnection();
            //获得信道
            final Channel channel = conn.createChannel();
            //声明交换器
            String exchangeName = "hello-exchange";
            channel.exchangeDeclare(exchangeName, "direct", true);
            //声明队列
            String queueName = channel.queueDeclare().getQueue();
            String routingKey = "hola";
            //绑定队列，通过键 hola 将队列和交换器绑定起来
            channel.queueBind(queueName, exchangeName, routingKey);

            while (true) {
                //消费消息
                boolean autoAck = false;
                String consumerTag = "";
                channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        System.out.println("消费的路由键：" + routingKey);
                        System.out.println("消费的内容类型：" + contentType);
                        long deliveryTag = envelope.getDeliveryTag();
                        //确认消息
                        channel.basicAck(deliveryTag, false);
                        System.out.println("消费的消息体内容：");
                        String bodyStr = new String(body, "UTF-8");
                        System.out.println(bodyStr);

                    }
                });
            }
        }catch(Exception e){
            System.out.println("Exception happen!");
        }
    }
}
