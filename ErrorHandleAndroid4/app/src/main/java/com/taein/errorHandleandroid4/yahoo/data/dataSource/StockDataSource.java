package com.taein.errorHandleandroid4.yahoo.data.dataSource;

import com.taein.errorHandleandroid4.StockUpdate;

import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

public interface StockDataSource {

    Single<StockUpdate> getStockUpdateById(int id);

    void insert(StockUpdate stockUpdate);

    Flowable<StockUpdate> getStockUpdates();
}
