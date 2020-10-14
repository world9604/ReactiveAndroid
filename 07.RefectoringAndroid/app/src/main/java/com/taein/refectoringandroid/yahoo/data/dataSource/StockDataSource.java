package com.taein.refectoringandroid.yahoo.data.dataSource;

import com.taein.refectoringandroid.StockUpdate;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);

    Flowable<StockUpdate> getStockUpdates();
}
