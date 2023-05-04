package com.brokerdemo.brokerconvertdemoproject.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: houyunlu
 **/
@Entity
@Table(name = "kyc_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KycUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "kyc_info")
    private String kycInfo;
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;

}
