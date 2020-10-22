package com.taein.sweettrackerassignment.data.dao;

import com.taein.sweettrackerassignment.data.entity.ParcelInfoWithTrackingDetails;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetailsVO;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface SweetTrackerService {

    @GET("image/mobile_test/mobile.json")
    Flowable<ParcelInfoWithTrackingDetailsVO> getParcelInfoFromQuery();
}
