package com.taein.refectoringandroid.yahoo;

import com.taein.refectoringandroid.yahoo.json.YahooStockResult;

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
