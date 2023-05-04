package com.brokerdemo.brokerconvertdemoproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: houyunlu
 * @date: 4/9/23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrDTO {

    private String ocrBase64Front64Str;
    private String ocrBase64BackStr;
}
