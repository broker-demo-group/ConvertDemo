package com.brokerdemo.brokerconvertdemoproject.dao;

import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SubAccountRepository extends MongoRepository<SubAccount,String> {

    @Query("{belongUserId:'?0'}")
    User findSubAccountByUserName(String userName);

}
