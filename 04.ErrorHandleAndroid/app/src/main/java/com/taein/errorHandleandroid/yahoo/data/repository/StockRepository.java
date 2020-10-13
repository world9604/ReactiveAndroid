package com.taein.errorHandleandroid.yahoo.data.repository;

import android.app.Application;

import com.taein.errorHandleandroid.StockUpdate;
import com.taein.errorHandleandroid.yahoo.data.StockDatabase;
import com.taein.errorHandleandroid.yahoo.data.StockUpdateDao;
import com.taein.errorHandleandroid.yahoo.data.dataSource.LocalStockDataSource;
import com.taein.errorHandleandroid.yahoo.data.dataSource.RemoteStockDataSource;
import com.taein.errorHandleandroid.yahoo.data.dataSource.StockDataSource;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class StockRepository {

    private StockDataSource localDataSource;
    private StockDataSource remoteDataSource;
    private static volatile StockRepository INSTANCE;

    public StockRepository(Application app) {
        StockDatabase db = StockDatabase.getInstance(app);
        StockUpdateDao stockUpdateDao = db.stockUpdateDao();

        localDataSource = LocalStockDataSource.getInstance(stockUpdateDao);
        remoteDataSource = RemoteStockDataSource.getInstance(stockUpdateDao);
    }

    // 싱글톤 객체 반환 메소드
    public static StockRepository getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (StockRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StockRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    public Single<StockUpdate> getStockUpdateById(int id) {
        return localDataSource.getStockUpdateById(id);
    }

    public void insert(StockUpdate stockUpdate) {
        localDataSource.insert(stockUpdate);
    }

    public Flowable<StockUpdate> getStockUpdates() {
        return localDataSource.getStockUpdates();
    }
}
