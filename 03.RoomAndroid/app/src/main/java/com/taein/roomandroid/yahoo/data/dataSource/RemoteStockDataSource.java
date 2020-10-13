package com.taein.roomandroid.yahoo.data.dataSource;

import androidx.annotation.NonNull;

import com.taein.roomandroid.StockUpdate;
import com.taein.roomandroid.yahoo.data.StockUpdateDao;

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
}
