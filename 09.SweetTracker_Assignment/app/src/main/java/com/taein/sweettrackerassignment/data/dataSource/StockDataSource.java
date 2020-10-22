package com.taein.sweettrackerassignment.data.dataSource;

import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.ParcelInfoWithTrackingDetails;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetailsVO;

import io.reactivex.Flowable;

public interface StockDataSource {

    ParcelInfoWithTrackingDetails getStockUpdateById(int id);

    void insert(ParcelInfo stockUpdate);

    ParcelInfoWithTrackingDetails getStockUpdates();

    Flowable<ParcelInfoWithTrackingDetailsVO> getParcelVOFromQuery();
}

