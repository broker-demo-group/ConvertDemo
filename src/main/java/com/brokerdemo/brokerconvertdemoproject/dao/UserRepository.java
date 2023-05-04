package com.brokerdemo.brokerconvertdemoproject.dao;

import com.brokerdemo.brokerconvertdemoproject.dao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserName(String userName);

}
