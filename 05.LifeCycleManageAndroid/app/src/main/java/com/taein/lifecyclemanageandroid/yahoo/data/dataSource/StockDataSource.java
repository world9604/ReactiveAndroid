package com.taein.lifecyclemanageandroid.yahoo.data.dataSource;

import com.taein.lifecyclemanageandroid.StockUpdate;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);

    Flowable<StockUpdate> getStockUpdates();
}
