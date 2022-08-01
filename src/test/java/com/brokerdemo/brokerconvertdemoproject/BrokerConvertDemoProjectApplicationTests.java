package com.brokerdemo.brokerconvertdemoproject;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.service.UserService;
import com.brokerdemo.brokerconvertdemoproject.utils.SnowflakeIdgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrokerConvertDemoProjectApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SnowflakeIdgenerator idgenerator;
    @Autowired
    UserService newUserCreator;
}
