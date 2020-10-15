package com.taein.sweettrackerassignment.data.dataSource;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.taein.sweettrackerassignment.data.dao.SweetTrackerService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitYahooServiceFactory {

//    final String SWEET_TRACKER_BASE_URL = "http://img.sweettracker.net/image/mobile_test/mobile.json/";
    final String SWEET_TRACKER_BASE_URL = "http://img.sweettracker.net/";

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SWEET_TRACKER_BASE_URL)
            .build();

    public SweetTrackerService create() {
        return retrofit.create(SweetTrackerService.class);
    }
}
