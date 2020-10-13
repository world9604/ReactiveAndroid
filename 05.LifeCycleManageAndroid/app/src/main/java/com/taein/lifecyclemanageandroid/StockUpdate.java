package com.taein.lifecyclemanageandroid;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.taein.lifecyclemanageandroid.yahoo.json.YahooStockQuote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import twitter4j.Status;

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

    @ColumnInfo(name = "twitterStatus")
    @Getter
    private String twitterStatus;

    public StockUpdate(String stockSymbol, BigDecimal price, Date date, String twitterStatus) {
        if (stockSymbol == null) this.stockSymbol = "";
        if (twitterStatus == null) this.twitterStatus = "";
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
        this.twitterStatus = twitterStatus;
    }

    public static StockUpdate create(YahooStockQuote r) {
        return new StockUpdate(r.getSymbol(), r.getLastTradePriceOnly(), new Date(), "");
    }

    public static StockUpdate create(Status status) {
        return new StockUpdate("", BigDecimal.ZERO, status.getCreatedAt(), status.getText());
    }

    public boolean isTwitterStatusUpdate() {
        return !twitterStatus.isEmpty();
    }
}