package com.brokerdemo.brokerconvertdemoproject.service;

import com.brokerdemo.brokerconvertdemoproject.constant.KycLevelEnum;
import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycIdCardDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycResult;
import java.util.List;

/**
 * @author: houyunlu
 **/
public interface KycService {

    Boolean verifyIdCardRealName(String cardNo, String realName, String userName);

    /**
     *
     * @param ocrBase64Front64Str  Front of ID card (base64Str of picture)
     * @param ocrBase64BackStr Reverse of ID card (base64Str of picture)
     * @return result
     */
    Boolean verifyOcr(String ocrBase64Front64Str, String ocrBase64BackStr,String userName);

    KycLevelEnum getUserLevel(String userName);

}
