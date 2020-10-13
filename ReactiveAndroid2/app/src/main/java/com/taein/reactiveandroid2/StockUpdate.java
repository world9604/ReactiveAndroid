package com.taein.reactiveandroid2;

import com.taein.reactiveandroid2.yahoo.json.YahooStockQuote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

public class StockUpdate implements Serializable {
    @Getter
    private final String stockSymbol;
    @Getter
    private final BigDecimal price;
    @Getter
    private final Date date;

    public StockUpdate(String stockSymbol, BigDecimal price, Date date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public static StockUpdate create(YahooStockQuote r) {
        return new StockUpdate(r.getSymbol(), r.getLastTradePriceOnly(), new Date());
    }
}