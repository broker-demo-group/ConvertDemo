package com.brokerdemo.brokerconvertdemoproject.dao;

import com.brokerdemo.brokerconvertdemoproject.dao.domain.KycUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: houyunlu
 **/
public interface KycUserRepository extends JpaRepository<KycUser,Long> {

    KycUser findKycUserByUserId(Long userId);
}
