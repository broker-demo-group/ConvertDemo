package com.brokerdemo.brokerconvertdemoproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdCardFrontDTO {

    private String idcardno;
    private String name;
    private String nationality;
    private String sex;
    private String birth;
    private String address;
    private String imageStatus;
    private String direction;
}
