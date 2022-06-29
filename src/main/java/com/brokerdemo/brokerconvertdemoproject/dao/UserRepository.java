package com.brokerdemo.brokerconvertdemoproject.dao;

import com.brokerdemo.brokerconvertdemoproject.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface UserRepository extends MongoRepository<User,String> {

    @Query("{_id:'?0'}")
    User findUserByUserName(String userName);

}
