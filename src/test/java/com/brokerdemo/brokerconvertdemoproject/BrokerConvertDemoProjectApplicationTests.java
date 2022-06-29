package com.brokerdemo.brokerconvertdemoproject;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrokerConvertDemoProjectApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    UserRepository userRepository;
    @Test
    public void testMongo(){

    }
}
