package com.brokerdemo.brokerconvertdemoproject.utils;

import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MongoUserDetail implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.brokerdemo.brokerconvertdemoproject.entity.User mongoUser = userRepository.findUserByUserName(username);
        if(mongoUser == null){
            throw new UsernameNotFoundException("User: " + username + "Not found!");
        }
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        for(String role:mongoUser.getPrivilage()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(mongoUser.getUserName(),mongoUser.getPassWord(),!mongoUser.isDisable(),true,true,true,grantedAuthorities);

    }
}
