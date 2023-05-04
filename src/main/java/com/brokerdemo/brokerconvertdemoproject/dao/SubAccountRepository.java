package com.brokerdemo.brokerconvertdemoproject.dao;

import com.brokerdemo.brokerconvertdemoproject.dao.domain.SubAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubAccountRepository extends JpaRepository<SubAccount,Long> {

    SubAccount findSubAccountByBelongUserId(String belongUserId);

}
