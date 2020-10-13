package com.taein.errorHandleandroid;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.taein.errorHandleandroid.yahoo.json.YahooStockQuote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;

@Entity(tableName = "stock_updates")
public class StockUpdate implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Getter
    private int id;

    @ColumnInfo(name = "stockSymbol")
    @Getter
    private String stockSymbol;

    @ColumnInfo(name = "price")
    @Getter
    private BigDecimal price;

    @ColumnInfo(name = "date")
    @Getter
    private Date date;

    public StockUpdate(String stockSymbol, BigDecimal price, Date date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public static StockUpdate create(YahooStockQuote r) {
        return new StockUpdate(r.getSymbol(), r.getLastTradePriceOnly(), new Date());
    }
}