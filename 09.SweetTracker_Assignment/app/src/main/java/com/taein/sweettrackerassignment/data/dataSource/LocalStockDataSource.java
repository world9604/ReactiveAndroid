package com.taein.sweettrackerassignment.data.dataSource;

import androidx.annotation.NonNull;
import com.taein.sweettrackerassignment.data.dao.ParcelInfoDao;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;

import io.reactivex.Single;


public class LocalStockDataSource implements StockDataSource{

    private static volatile LocalStockDataSource INSTANCE;
    private ParcelInfoDao parcelInfoDao;

    public LocalStockDataSource(ParcelInfoDao parcelInfoDao) {
        this.parcelInfoDao = parcelInfoDao;
    }

    public static LocalStockDataSource getInstance(@NonNull ParcelInfoDao parcelInfoDao) {
        if (INSTANCE == null) {
            synchronized (LocalStockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalStockDataSource(parcelInfoDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public ParcelInfoWithTrackingDetails getStockUpdateById(int id) {
        return parcelInfoDao.getParcelInfoWithTrackingDetailsById(id);
    }

    @Override
    public void insert(ParcelInfo stockUpdate) {
        parcelInfoDao.insert(stockUpdate);
    }

    @Override
    public ParcelInfoWithTrackingDetails getStockUpdates() {
        return parcelInfoDao.getParcelInfos();
    }

    @Override
    public Single<ParcelInfoWithTrackingDetails> getParcelVOFromQuery() {
        return null;
    }
}
