package com.taein.sweettrackerassignment.data.dataSource;

import androidx.annotation.NonNull;

import com.taein.sweettrackerassignment.data.dao.SweetTrackerService;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RemoteStockDataSource implements StockDataSource{

    private static volatile RemoteStockDataSource INSTANCE;
    private SweetTrackerService sweetTrackerService;

    private RemoteStockDataSource(SweetTrackerService sweetTrackerService){
        this.sweetTrackerService = sweetTrackerService;
    }

    public static RemoteStockDataSource getInstance(@NonNull SweetTrackerService sweetTrackerService) {
        if (INSTANCE == null) {
            synchronized (RemoteStockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteStockDataSource(sweetTrackerService);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<ParcelInfo> getStockUpdateById(int id) {
        return sweetTrackerService.getStockUpdateById(id);
    }

    @Override
    public void insert(ParcelInfo stockUpdate) {
        sweetTrackerService.insert(stockUpdate);
    }

    @Override
    public Flowable<ParcelInfo> getStockUpdates() {
        return sweetTrackerService.getStockUpdates();
    }

    @Override
    public Observable<ParcelInfo> getParcelInfoFromQuery(String query) {
        return sweetTrackerService.getParcelInfoFromQuery(query);
    }
}
