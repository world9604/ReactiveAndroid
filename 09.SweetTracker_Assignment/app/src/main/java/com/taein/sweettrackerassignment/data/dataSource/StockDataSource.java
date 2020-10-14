package com.taein.sweettrackerassignment.data.dataSource;

import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface StockDataSource {

    Single<ParcelInfo> getStockUpdateById(int id);

    void insert(ParcelInfo stockUpdate);

    Observable<ParcelInfo> getStockUpdates();

    ParcelInfo getParcelInfoFromQuery(String query);
}

