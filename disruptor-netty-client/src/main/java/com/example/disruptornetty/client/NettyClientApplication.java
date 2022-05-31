package com.example.disruptornetty.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
        new NettyClient().sendData();
    }

}
