package com.brokerdemo.brokerconvertdemoproject.utils;

import cn.hutool.core.bean.BeanUtil;
import com.brokerdemo.brokerconvertdemoproject.dao.domain.SubAccount;
import org.okxbrokerdemo.utils.APIKeyHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    private UserUtil() {}


    public static APIKeyHolder generateAPIKeyHolder(SubAccount subAccount){
        // TODO 默认为模拟盘
        APIKeyHolder apiKeyHolder = APIKeyHolder.builder().isSimluate(true).build();
        BeanUtil.copyProperties(subAccount, apiKeyHolder);
        return apiKeyHolder;
    }
}
