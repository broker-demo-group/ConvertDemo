package com.brokerdemo.brokerconvertdemoproject.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("subAccounts")

public class SubAccount {
    @Id
    private String subAccountId;
    private String belongUserId;
    private String apiKey;
    private String passphrase;
    private String apiSecret;
    public String getBelongUserId() {
        return belongUserId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public SubAccount(){}

    public SubAccount(String belongUserId, String subAccountName) {
        this.belongUserId = belongUserId;
        this.subAccountName = subAccountName;
    }

    public void setBelongUserId(String belongUserId) {
        this.belongUserId = belongUserId;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
    }

    private String subAccountName;
}
