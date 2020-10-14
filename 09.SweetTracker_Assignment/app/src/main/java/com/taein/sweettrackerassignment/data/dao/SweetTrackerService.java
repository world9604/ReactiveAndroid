package com.taein.sweettrackerassignment.data.dao;

import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SweetTrackerService {

    @GET()
    Single<ParcelInfo> getParcelInfoFromQuery(
        @Query("") String query
    );
}
