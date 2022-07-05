package com.brokerdemo.brokerconvertdemoproject.configuration;

import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/4  12:22 PM
 **/
@Configuration
public class BrokerConfiguration {
    @Value("${broker.api.apiKey}")
    String apiKey;
    @Value("${broker.api.apiSecretKey}")
    String apiSecretKey;
    @Value("${broker.api.passPhrase}")
    String passPhrase;
    @Value("${broker.api.isSimulate}")
    boolean isSimulate;

    @Bean
    public Client brokerClient(){
        return OkxSDK.getClient(apiKey,apiSecretKey,passPhrase,isSimulate);
    }
}
