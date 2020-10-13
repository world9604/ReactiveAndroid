package com.taein.roomandroid.yahoo;

import com.taein.roomandroid.yahoo.json.YahooStockResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YahooService {

    @GET("yql?format=json")
    Single<YahooStockResult> yqlQuery(
            @Query("q") String query,
            @Query("env") String env
    );
}
