package com.taein.functionalprogrammingandroid.yahoo.json;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import lombok.Getter;

public class YahooStockQuote {
    @Getter
    private String symbol;

    @Getter
    @SerializedName("Name")
    private String name;

    @Getter
    @SerializedName("LastTradePriceOnly")
    private BigDecimal lastTradePriceOnly;

    @Getter
    @SerializedName("DaysLow")
    private BigDecimal daysLow;

    @Getter
    @SerializedName("DaysHigh")
    private BigDecimal daysHigh;

    @Getter
    @SerializedName("Volume")
    private String volume;
}
