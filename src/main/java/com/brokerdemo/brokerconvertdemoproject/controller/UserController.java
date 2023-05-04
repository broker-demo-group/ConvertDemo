package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.cache.Cache;
import com.brokerdemo.brokerconvertdemoproject.constant.KycLevelEnum;
import com.brokerdemo.brokerconvertdemoproject.dao.domain.User;
import com.brokerdemo.brokerconvertdemoproject.dto.request.LoginDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.request.OcrKycRequest;
import com.brokerdemo.brokerconvertdemoproject.dto.request.RealNameKycRequest;
import com.brokerdemo.brokerconvertdemoproject.dto.request.RegisterDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycIdCardDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.LoginResDTO;
import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.KycService;
import com.brokerdemo.brokerconvertdemoproject.service.UserService;
import com.brokerdemo.brokerconvertdemoproject.utils.JwtTokenUtil;
import com.brokerdemo.brokerconvertdemoproject.utils.SecurityUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @author: houyunlu
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private KycService kycService;

    @Autowired
    private UserService userService;

    @PostMapping("/kyc/realname")
    public BrokerResponse<AliKycIdCardDTO> kyc(@RequestBody RealNameKycRequest realNameKycRequest) {
        Boolean success = kycService.verifyIdCardRealName(realNameKycRequest.getCardNo(),
                realNameKycRequest.getRealName(), realNameKycRequest.getUserName());
        return BrokerResponse.success(success);
    }

    @PostMapping("/kyc/ocr")
    public BrokerResponse<Boolean> kyc(@RequestBody OcrKycRequest ocrKycRequest) {
        Boolean success = kycService.verifyOcr(ocrKycRequest.getOcrBase64Front64Str(),
                ocrKycRequest.getOcrBase64BackStr(), ocrKycRequest.getUserName());
        return BrokerResponse.success(success);
    }

    @GetMapping("/kyc/level")
    public BrokerResponse<Integer> getKycLevel(@RequestParam String userName) {
        KycLevelEnum userLevel = kycService.getUserLevel(userName);
        return BrokerResponse.success(userLevel.getValue());
    }


    @PostMapping("/register")
    @ResponseBody
    public BrokerResponse register(@RequestBody RegisterDTO register) {
        Date now = new Date();

        User user = User.builder()
                .userName(register.getUsername())
                .passWord(register.getPassword())
                .registerDate(now)
                .isDisable(false)
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .createTime(now)
                .modifyTime(now)
                .build();
        userService.createCommonUser(user);
        return BrokerResponse.success();
    }

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private Cache cache;

    @PostMapping("/login")
    public BrokerResponse<LoginResDTO> login(@RequestBody LoginDTO loginReq) {
        Authentication authToken = SecurityUtils.login(loginReq.getUserName(), loginReq.getPassword(), authenticationManager);
        String token = JwtTokenUtil.getToken(authToken.getName());
        cache.set(token, new Gson().toJson(loginReq));
        return BrokerResponse.success(LoginResDTO.builder().token(token).build());
    }
}
