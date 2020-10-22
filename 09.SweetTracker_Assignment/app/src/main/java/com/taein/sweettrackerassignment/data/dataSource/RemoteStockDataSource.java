package com.taein.sweettrackerassignment.data.dataSource;

import androidx.annotation.NonNull;

import com.taein.sweettrackerassignment.data.dao.SweetTrackerService;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.ParcelInfoWithTrackingDetails;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetailsVO;

import io.reactivex.Flowable;

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
    public ParcelInfoWithTrackingDetails getStockUpdateById(int id) {
        return new ParcelInfoWithTrackingDetails();
    }

    @Override
    public void insert(ParcelInfo stockUpdate) {}

    @Override
    public ParcelInfoWithTrackingDetails getStockUpdates() {
        return new ParcelInfoWithTrackingDetails();
    }

    @Override
    public Flowable<ParcelInfoWithTrackingDetailsVO> getParcelVOFromQuery() {
        return sweetTrackerService.getParcelInfoFromQuery();
    }
}
