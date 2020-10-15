package com.taein.sweettrackerassignment.data.repository;

import android.app.Application;
import com.taein.sweettrackerassignment.data.dataSource.RetrofitYahooServiceFactory;
import com.taein.sweettrackerassignment.data.dao.SweetTrackerService;
import com.taein.sweettrackerassignment.data.ParcelInfoDatabase;
import com.taein.sweettrackerassignment.data.dao.ParcelInfoDao;
import com.taein.sweettrackerassignment.data.dataSource.LocalStockDataSource;
import com.taein.sweettrackerassignment.data.dataSource.RemoteStockDataSource;
import com.taein.sweettrackerassignment.data.dataSource.StockDataSource;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;

import io.reactivex.Single;

public class ParcelInfoRepository {

    private StockDataSource localDataSource;
    private StockDataSource remoteDataSource;
    private static volatile ParcelInfoRepository INSTANCE;

    public ParcelInfoRepository(Application app) {
        ParcelInfoDatabase db = ParcelInfoDatabase.getInstance(app);

        // Local Data Source
        ParcelInfoDao parcelInfoDao = db.ParcelInfoDao();
        localDataSource = LocalStockDataSource.getInstance(parcelInfoDao);

        // Remote Data Source
        SweetTrackerService sweetTrackerService = new RetrofitYahooServiceFactory().create();
        remoteDataSource = RemoteStockDataSource.getInstance(sweetTrackerService);
    }

    // 싱글톤 객체 반환 메소드
    public static ParcelInfoRepository getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (ParcelInfoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ParcelInfoRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    public ParcelInfoWithTrackingDetails getStockUpdateById(int id) {
        return localDataSource.getStockUpdateById(id);
    }

    public void insert(ParcelInfo parcelInfoWithTrackingDetails) {
        localDataSource.insert(parcelInfoWithTrackingDetails);
    }

    public ParcelInfoWithTrackingDetails getStockUpdates() {
        return localDataSource.getStockUpdates();
    }

    public Single<ParcelInfoWithTrackingDetails> getParcelVOFromQuery() {
        return remoteDataSource.getParcelVOFromQuery();
    }
}
