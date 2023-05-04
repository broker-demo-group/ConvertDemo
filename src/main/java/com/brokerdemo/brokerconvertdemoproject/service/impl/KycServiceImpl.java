package com.brokerdemo.brokerconvertdemoproject.service.impl;

import com.brokerdemo.brokerconvertdemoproject.constant.KycLevelEnum;
import com.brokerdemo.brokerconvertdemoproject.dao.KycUserRepository;
import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.dao.domain.KycUser;
import com.brokerdemo.brokerconvertdemoproject.dao.domain.User;
import com.brokerdemo.brokerconvertdemoproject.dto.KycInfo;
import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycIdCardDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycResult;
import com.brokerdemo.brokerconvertdemoproject.dto.response.IdCardBackDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.IdCardFrontDTO;
import com.brokerdemo.brokerconvertdemoproject.service.KycService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.Objects;

/**
 * @author: houyunlu
 **/
@Slf4j
@Service
public class KycServiceImpl implements KycService {

    private static final String ALI_YUN_KYC_HOST = "https://zidv2.market.alicloudapi.com";
    private static final String ALI_YUN_KYC_PATH = "/idcheck/Post";
    private static final String ALI_OCR_FRONT_PATH = "/thirdnode/ImageAI/idcardfrontrecongnition";
    private static final String ALI_OCR_BACK_PATH = "/thirdnode/ImageAI/idcardbackrecongnition";
    private static final String ALI_YUN_AUTHORIZATION = "APPCODE 361fde44b48c44e086824651c455aa4d";

    private static final Integer SUCCESS_CODE = 0;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KycUserRepository kycUserRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyIdCardRealName(String cardNo, String realName, String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (Objects.isNull(user)) {
            return false;
        }

        KycUser kycUser = kycUserRepository.findKycUserByUserId(user.getId());
        if (Objects.nonNull(kycUser)) {
            return true;
        }

        HttpHeaders header = getAliyunKycRequestHeader();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("cardNo", cardNo);
        paramMap.add("realName", realName);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramMap, header);
        String result = restTemplate.postForObject(ALI_YUN_KYC_HOST + ALI_YUN_KYC_PATH, request, String.class);
        Gson gson = new Gson();
        AliKycResult<AliKycIdCardDTO> kycResult = gson.fromJson(result, new TypeToken<AliKycResult<AliKycIdCardDTO>>() {
        }.getType());
        if (SUCCESS_CODE.equals(kycResult.getError_code())) {
            Date now = new Date();
            KycInfo kycInfo = KycInfo.builder()
                    .idCardInfo(kycResult.getResult())
                    .build();
            kycUserRepository.save(KycUser.builder()
                    .level(KycLevelEnum.LEVEL_1.getValue())
                    .userId(user.getId())
                    .kycInfo(gson.toJson(kycInfo))
                    .createTime(now)
                    .modifyTime(now)
                    .build());
            return true;
        }

        return false;
    }

    @Override
    public Boolean verifyOcr(String ocrBase64Front64Str, String ocrBase64BackStr, String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (Objects.isNull(user)) {
            return false;
        }

        KycUser kycUser = kycUserRepository.findKycUserByUserId(user.getId());
        if (Objects.isNull(kycUser)) {
            return false;
        }
        Integer level = kycUser.getLevel();
        if (KycLevelEnum.LEVEL_2.getValue().equals(level)){
            return true;
        }

        HttpHeaders header = getAliyunKycRequestHeader();
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();;
        paramMap.add("base64Str", ocrBase64Front64Str);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramMap, header);
        String result = restTemplate.postForObject(ALI_YUN_KYC_HOST + ALI_OCR_FRONT_PATH, request, String.class);
        Gson gson = new Gson();
        AliKycResult<IdCardFrontDTO> ocrFrontResult = gson.fromJson(result, new TypeToken<AliKycResult<IdCardFrontDTO>>() {
        }.getType());
        if (Objects.isNull(ocrFrontResult) || !SUCCESS_CODE.equals(ocrFrontResult.getError_code())) {
            return false;
        }

        paramMap.add("base64Str", ocrBase64BackStr);
        request = new HttpEntity<>(paramMap, header);
        result = restTemplate.postForObject(ALI_YUN_KYC_HOST + ALI_OCR_BACK_PATH, request, String.class);
        AliKycResult<IdCardBackDTO> ocrBackResult = gson.fromJson(result, new TypeToken<AliKycResult<IdCardBackDTO>>() {
        }.getType());
        if (Objects.isNull(ocrBackResult) || !SUCCESS_CODE.equals(ocrBackResult.getError_code())) {
            return false;
        }

        kycUser.setLevel(KycLevelEnum.LEVEL_2.getValue());
        KycInfo kycInfo = gson.fromJson(kycUser.getKycInfo(), KycInfo.class);
        kycInfo.setIdCardFrontInfo(ocrFrontResult.getResult());
        kycInfo.setIdCardBackInfo(ocrBackResult.getResult());
        kycUser.setKycInfo(gson.toJson(kycInfo));
        kycUserRepository.save(kycUser);
        return true;
    }

    @Override
    public KycLevelEnum getUserLevel(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (Objects.isNull(user)) {
            return KycLevelEnum.DEFAULT;
        }

        KycUser kycUser = kycUserRepository.findKycUserByUserId(user.getId());
        if (Objects.isNull(kycUser)) {
            return KycLevelEnum.DEFAULT;
        }

        return KycLevelEnum.getByValue(kycUser.getLevel());
    }


    private HttpHeaders getAliyunKycRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", ALI_YUN_AUTHORIZATION);
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return header;
    }
}
