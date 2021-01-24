package com.mina.npay.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.mina.npay.gateway.mapper*")
public class PayGwApplication {

    public static void main(String args[]) {
        SpringApplication.run(PayGwApplication.class, args);
    }

}
