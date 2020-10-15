package com.taein.sweettrackerassignment.data.dao;

import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SweetTrackerService {

    @GET("image/mobile_test/mobile.json")
    Single<ParcelInfoWithTrackingDetails> getParcelInfoFromQuery();
}
