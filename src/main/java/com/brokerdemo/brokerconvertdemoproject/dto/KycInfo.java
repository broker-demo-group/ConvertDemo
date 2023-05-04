package com.brokerdemo.brokerconvertdemoproject.dto;

import com.brokerdemo.brokerconvertdemoproject.dto.response.AliKycIdCardDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.IdCardBackDTO;
import com.brokerdemo.brokerconvertdemoproject.dto.response.IdCardFrontDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 * @date: 5/4/23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KycInfo {

    private AliKycIdCardDTO idCardInfo;
    private IdCardBackDTO idCardBackInfo;
    private IdCardFrontDTO idCardFrontInfo;

}
