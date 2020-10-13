package com.taein.reactiveandroid2.yahoo.json;

import java.util.List;

import lombok.Getter;

public class YahooStockResults {

    @Getter
    private List<YahooStockQuote> quote;
}
