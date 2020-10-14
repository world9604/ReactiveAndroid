package com.taein.functionalprogrammingandroid.yahoo.json;

import java.util.List;

import lombok.Getter;

public class YahooStockResults {

    @Getter
    private List<YahooStockQuote> quote;
}
