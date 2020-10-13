package com.taein.errorHandleandroid.yahoo.data.dataSource;

import com.taein.errorHandleandroid.StockUpdate;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);

    Flowable<StockUpdate> getStockUpdates();
}
