package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.utils.snowflakeIdgenerator;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewUserCreator {
    @Autowired
    UserRepository userRepository;
    @Resource
    SubAccountRepository subAccountRepository;
    @Autowired
    snowflakeIdgenerator idgenerator;
    public boolean createCommonUser(User user){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));
        List<String> privilage = new ArrayList<>();
        privilage.add("ROLE_USER");
        user.setPrivilage(privilage);
        try{
            userRepository.insert(user);
        }catch (DuplicateKeyException e){
            return false;
        }
        SubAccount subAccount = new SubAccount(user.getUserName(),"x"+String.valueOf(idgenerator.nextId()));
        
        return true;
    }
}
