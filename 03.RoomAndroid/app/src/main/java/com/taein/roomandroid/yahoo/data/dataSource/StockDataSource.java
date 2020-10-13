package com.taein.roomandroid.yahoo.data.dataSource;

import com.taein.roomandroid.StockUpdate;

import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);
}
