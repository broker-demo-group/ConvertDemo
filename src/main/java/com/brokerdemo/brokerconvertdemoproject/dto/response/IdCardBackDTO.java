package com.brokerdemo.brokerconvertdemoproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: houyunlu
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdCardBackDTO {

    private String startDate;
    private String endDate;
    private String issue;
    private String imageStatus;
    private String direction;

}
