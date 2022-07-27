package com.brokerdemo.brokerconvertdemoproject;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.service.NewUserCreator;
import com.brokerdemo.brokerconvertdemoproject.utils.snowflakeIdgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrokerConvertDemoProjectApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    snowflakeIdgenerator idgenerator;
    @Autowired
    NewUserCreator newUserCreator;
}
