package com.rbs.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: OOAD究极玩具系统
 * @Author: WinstonDeng& 17Wang
 * @Date: 10:35 2018/12/29
 */
@SpringBootApplication
@MapperScan(basePackages = "com.rbs.project.mapper")
@EnableTransactionManagement
@EnableAutoConfiguration
public class ProjectApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ProjectApplication.class, args);
        //WebSocket.setApplicationContext(applicationContext);
    }
}

