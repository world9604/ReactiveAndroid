package com.taein.roomandroid.yahoo.json;

import java.util.Date;

import lombok.Getter;

public class YahooStockQuery {

    @Getter
    private int count;
    @Getter
    private Date created;
    @Getter
    private YahooStockResults results;
}
