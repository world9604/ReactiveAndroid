package com.taein.roomandroid3.yahoo.data.dataSource;

import androidx.annotation.NonNull;

import com.taein.roomandroid3.StockUpdate;
import com.taein.roomandroid3.yahoo.data.StockUpdateDao;

import io.reactivex.Single;

public class LocalStockDataSource implements StockDataSource{

    private static volatile LocalStockDataSource INSTANCE;
    private StockUpdateDao stockUpdateDao;

    public LocalStockDataSource(StockUpdateDao stockUpdateDao) {
        this.stockUpdateDao = stockUpdateDao;
    }

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
