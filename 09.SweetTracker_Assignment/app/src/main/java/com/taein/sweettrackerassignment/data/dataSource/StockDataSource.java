package com.taein.sweettrackerassignment.data.dataSource;

import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;

import io.reactivex.Single;

public interface StockDataSource {

    ParcelInfoWithTrackingDetails getStockUpdateById(int id);

    void insert(ParcelInfo stockUpdate);

    ParcelInfoWithTrackingDetails getStockUpdates();

    Single<ParcelInfoWithTrackingDetails> getParcelVOFromQuery();
}

