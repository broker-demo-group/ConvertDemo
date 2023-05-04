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

@Slf4j
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.brokerdemo.brokerconvertdemoproject.dao.domain.User user = userRepository.findUserByUserName(username);
        user = new com.brokerdemo.brokerconvertdemoproject.dao.domain.User();
        user.setUserName(username);
        user.setPassWord("123456");
        user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));

        return new User(user.getUserName(), user.getPassWord(), !user.getIsDisable(), true, true, true, Lists.newArrayList());

    }
}
