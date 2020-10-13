package com.taein.errorHandleandroid.yahoo.data.dataSource;

import androidx.annotation.NonNull;

import com.taein.errorHandleandroid.StockUpdate;
import com.taein.errorHandleandroid.yahoo.data.StockUpdateDao;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RemoteStockDataSource implements StockDataSource{

    private static volatile LocalStockDataSource INSTANCE;
    private StockUpdateDao stockUpdateDao;

    public static LocalStockDataSource getInstance(@NonNull StockUpdateDao stockUpdateDao) {
        if (INSTANCE == null) {
            synchronized (LocalStockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalStockDataSource(stockUpdateDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<StockUpdate> getStockUpdateById(int id) {
        return stockUpdateDao.getStockUpdateById(id);
    }

    @Override
    public void insert(StockUpdate stockUpdate) {
        stockUpdateDao.insert(stockUpdate);
    }

    @Override
    public Flowable<StockUpdate> getStockUpdates() {
        return stockUpdateDao.getStockUpdates();
    }
}
