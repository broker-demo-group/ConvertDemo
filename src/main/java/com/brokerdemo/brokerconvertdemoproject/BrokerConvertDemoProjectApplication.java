package com.brokerdemo.brokerconvertdemoproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@EnableOpenApi
@SpringBootApplication
public class BrokerConvertDemoProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokerConvertDemoProjectApplication.class, args);
        log.info("brokerConvertDemo start success!!1");
    }

}
