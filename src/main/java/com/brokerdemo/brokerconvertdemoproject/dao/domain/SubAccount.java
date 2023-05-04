package com.brokerdemo.brokerconvertdemoproject.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sub_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubAccount {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "sub_accountId")
    private String subAccountId;
    @Column(name = "belong_userId")
    private String belongUserId;
    @Column(name = "sub_account_name")
    private String subAccountName;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "passphrase")
    private String passphrase;
    @Column(name = "api_secret")
    private String apiSecret;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

    // todo
    public SubAccount(String belongUserId, String subAccountName) {
        this.belongUserId = belongUserId;
        this.subAccountName = subAccountName;
    }

}
