package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.dao.ApiParam;
import com.brokerdemo.brokerconvertdemoproject.dao.SubAccountRepository;
import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.ApiSubAccountCreateDto;
import com.brokerdemo.brokerconvertdemoproject.entity.ApiSubAccountKeyCreateDto;
import com.brokerdemo.brokerconvertdemoproject.entity.SubAccount;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.utils.PassGenerator;
import com.brokerdemo.brokerconvertdemoproject.utils.snowflakeIdgenerator;
import com.mongodb.DuplicateKeyException;
import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class NewUserCreator {
    @Autowired
    UserRepository userRepository;
    @Resource
    SubAccountRepository subAccountRepository;
    @Autowired
    snowflakeIdgenerator idgenerator;
    @Value("${broker.api.apiKey}")
    String apiKey;
    @Value("${broker.api.apiSecretKey}")
    String apiSecretKey;
    @Value("${broker.api.passPhrase}")
    String passPhrase;
    @Value("${broker.api.isSimluate}")
    boolean isSimluate;
    @Value("${broker.api.maxRetry}")
    int MAX_Retry;
    public boolean createCommonUser(User user){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));
        List<String> privilage = new ArrayList<>();
        privilage.add("ROLE_USER");
        user.setPrivilage(privilage);

        SubAccount subAccount = null;
        Client client = OkxSDK.getClient(apiKey,apiSecretKey,passPhrase,isSimluate);
        int reTry=0;
        while(subAccount == null && reTry<=MAX_Retry){
            try{
                String subAccountName = "x"+idgenerator.toString();
                ApiParam param = new ApiParam();
                param.addParam("subAcct",subAccountName);
                ApiSubAccountCreateDto dto = client.getBrokerService().createSubAccount(param, ApiSubAccountCreateDto.class);
                SubAccount tmpAccount = new SubAccount(user.getUserName(),subAccountName);
                param = new ApiParam();
                param.addParam("subAcct",tmpAccount.getSubAccountName());
                param.addParam("label","BrokerDemoSystemAPIKey");
                param.addParam("passphrase", PassGenerator.generatePassword(16));
                param.addParam("perm","read_only,trade");
                ApiSubAccountKeyCreateDto apiSubAccountKeyCreateDto = client.getBrokerService().createSubAccountApiKey(param, ApiSubAccountKeyCreateDto.class);
                tmpAccount.setApiKey(apiSubAccountKeyCreateDto.getApiKey());
                tmpAccount.setApiSecret(apiSubAccountKeyCreateDto.getSecretKey());
                tmpAccount.setPassphrase(apiSubAccountKeyCreateDto.getPassphrase());
                subAccount = tmpAccount;
            }catch (Exception e){
                reTry+=1;
            }
        }
        if(subAccount == null){
            return false;
        }
        try{
            userRepository.insert(user);
            subAccountRepository.save(subAccount);
        }catch (DuplicateKeyException e){
            return false;
        }
        return true;
    }
}
