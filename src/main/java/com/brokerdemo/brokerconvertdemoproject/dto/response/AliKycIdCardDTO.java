package com.brokerdemo.brokerconvertdemoproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 * @date: 4/4/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliKycIdCardDTO {

    private String realname;
    private String idcard;
    private Boolean isok;
    private IdCardInfo IdCardInfor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdCardInfo {
        private String province;
        private String city;
        private String district;
        private String area;
        private String sex;
        private String birthday;
    }
}
