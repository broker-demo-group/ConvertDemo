package com.brokerdemo.brokerconvertdemoproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OcrKycRequest {

    private String ocrBase64Front64Str;
    private String ocrBase64BackStr;
    private String userName;

}
