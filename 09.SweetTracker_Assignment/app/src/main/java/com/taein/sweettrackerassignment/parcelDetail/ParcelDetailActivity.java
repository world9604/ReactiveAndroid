package com.taein.sweettrackerassignment.parcelDetail;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taein.sweettrackerassignment.databinding.ActivityParcelDetailBinding;
import com.taein.sweettrackerassignment.utils.ErrorHandler;
import com.taein.sweettrackerassignment.R;
import com.taein.sweettrackerassignment.data.repository.ParcelInfoRepository;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class ParcelDetailActivity extends RxAppCompatActivity {

    @Getter
    String helloText;
    private ParcelInfoRepository repository;
    private ActivityParcelDetailBinding binding;
    private LinearLayoutManager layoutManager;
    private TrackingDetailDataAdapter trackingDetailDataAdapter;
    private ParcelDetailViewModel viewModel;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parcel_detail);
        binding.setActivity(this);

        repository = ParcelInfoRepository.getInstance(getApplication());
//        viewModel = ParcelDetailActivity.obtainViewModel(requireActivity());

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        binding.stockUpdatesRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        binding.stockUpdatesRecyclerView.setLayoutManager(layoutManager);

        trackingDetailDataAdapter = new TrackingDetailDataAdapter();
        binding.stockUpdatesRecyclerView.setAdapter(trackingDetailDataAdapter);

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(s -> helloText = s);

        final String query = "http://img.sweettracker.net/image/mobile_test/mobile.json";

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .map(i -> repository.getParcelInfoFromQuery(query))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                })
                .observeOn(Schedulers.io())
                .doOnNext(this::saveParcelInfo)
                /*.onExceptionResumeNext(
                        repository.getStockUpdates()
                                .take(1)
                                .flatMap(Observable::fromIterable)
                )*/
                .doOnNext(update -> log(update))
                .map(v -> v.getTrackingDetails())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackingDetails -> {
                    Log.d("APP", "New update " + trackingDetails.toString());
                    binding.noDataAvailable.setVisibility(View.GONE);
                    trackingDetailDataAdapter.addAll(trackingDetails);
                    binding.stockUpdatesRecyclerView.smoothScrollToPosition(0);
                }, error -> {
                    if (trackingDetailDataAdapter.getItemCount() == 0) {
                        binding.noDataAvailable.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void saveParcelInfo(ParcelInfo parcelInfo) {
        Log.d("APP", String.format("parcelInfo : %s", parcelInfo.toString()));
        repository.insert(parcelInfo);
    }

    private void log(Throwable throwable) {
        Log.e("APP", "Error on " + Thread.currentThread().getName() + ":", throwable);
    }

    private void log(String stage, Throwable throwable) {
        Log.e("APP", stage + ":" + Thread.currentThread().getName() + ": error", throwable);
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage, int item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage, long item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }

    private void log(ParcelInfo update) {
        Log.d("APP", Thread.currentThread().getName() + ":" + update.toString());
    }

    private void log(long value) {
        Log.d("APP", Thread.currentThread().getName() + ":" + value);
    }
}