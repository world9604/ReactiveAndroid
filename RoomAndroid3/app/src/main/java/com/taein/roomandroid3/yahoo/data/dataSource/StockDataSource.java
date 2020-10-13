package com.taein.roomandroid3.yahoo.data.dataSource;

import com.taein.roomandroid3.StockUpdate;

import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);
}
