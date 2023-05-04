package com.brokerdemo.brokerconvertdemoproject.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: houyunlu
 **/
@Getter
@AllArgsConstructor
public enum KycLevelEnum {
    /**
     * kyc level
     */
    DEFAULT(0),
    LEVEL_1(1),
    LEVEL_2(2),
    ;

    private final Integer value;

    private static final Map<Integer, KycLevelEnum> kycLevelEnumMap =
            Arrays.stream(KycLevelEnum.values()).collect(Collectors.toMap(KycLevelEnum::getValue, Function.identity()));

    public static KycLevelEnum getByValue(Integer level) {
        return kycLevelEnumMap.getOrDefault(level, KycLevelEnum.DEFAULT);
    }
}
