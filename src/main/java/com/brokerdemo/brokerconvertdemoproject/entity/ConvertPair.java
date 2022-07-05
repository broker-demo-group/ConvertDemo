package com.brokerdemo.brokerconvertdemoproject.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: bowen
 * @description: 
 * @date: 2022/7/5  6:55 PM
 **/
@NoArgsConstructor
@Data
public class ConvertPair {

    @SerializedName("baseCcy")
    public String baseCcy;
    @SerializedName("baseCcyMax")
    public String baseCcyMax;
    @SerializedName("baseCcyMin")
    public String baseCcyMin;
    @SerializedName("instId")
    public String instId;
    @SerializedName("quoteCcy")
    public String quoteCcy;
    @SerializedName("quoteCcyMax")
    public String quoteCcyMax;
    @SerializedName("quoteCcyMin")
    public String quoteCcyMin;
}
