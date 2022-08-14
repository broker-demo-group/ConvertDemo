package com.brokerdemo.brokerconvertdemoproject.utils;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Slf4j
@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername 调用");
        com.brokerdemo.brokerconvertdemoproject.entity.User mongoUser = userRepository.findUserByUserName(username);
        if (Objects.isNull(mongoUser)){
            return null;
        }
        mongoUser.setPassWord(bCryptPasswordEncoder.encode(mongoUser.getPassWord()));

        return new User(mongoUser.getUserName(), mongoUser.getPassWord(), !mongoUser.isDisable(), true, true, true,
                Lists.newArrayList());

    }
}
