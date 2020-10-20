package com.taein.sweettrackerassignment.parcelDetail;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetails;
import com.taein.sweettrackerassignment.databinding.ActivityParcelDetailBinding;
import com.taein.sweettrackerassignment.utils.ErrorHandler;
import com.taein.sweettrackerassignment.R;
import com.taein.sweettrackerassignment.data.repository.ParcelInfoRepository;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

public class ParcelDetailActivity extends RxAppCompatActivity {

    public static final String SWEET_TRACKER_TAG = "SWEET_TRACKER_TAG";

    @Getter
    String helloText;
    private ParcelInfoRepository repository;
    private ActivityParcelDetailBinding binding;
    private TrackingDetailDataAdapter trackingDetailDataAdapter;
    private ParcelDetailViewModel viewModel;
    private ParcelInfo parcelInfo;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parcel_detail);
        binding.setParcelInfo(parcelInfo);

        repository = ParcelInfoRepository.getInstance(getApplication());
//        viewModel = ParcelDetailActivity.obtainViewModel(requireActivity());

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        trackingDetailDataAdapter = new TrackingDetailDataAdapter();
        binding.trackingDetailItemRecyclerView.setHasFixedSize(true);
        binding.trackingDetailItemRecyclerView.setAdapter(trackingDetailDataAdapter);
        binding.trackingDetailItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(s -> helloText = s);

        repository.getParcelVOFromQuery()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parcelInfoWithTrackingDetail -> {
                    Log.d(SWEET_TRACKER_TAG, "New update time : " + parcelInfoWithTrackingDetail.getParcelInfo().getParcelCompanyName());
                    trackingDetailDataAdapter.addAll(parcelInfoWithTrackingDetail.getTrackingDetails());
                    this.parcelInfo = parcelInfoWithTrackingDetail.getParcelInfo();
                });

        /*repository.getParcelVOFromQuery()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map(r -> r.getTrackingDetails())
                .flatMap(r -> Observable.fromIterable(r))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackingDetail -> {
                    Log.d(SWEET_TRACKER_TAG, "New update time : " + trackingDetail.getTime());
                    trackingDetailDataAdapter.add(trackingDetail);
                });*/


       /* Observable.interval(0, 5, TimeUnit.SECONDS)
                .map(i -> repository.getParcelVOFromQuery())
//                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
//                .doOnError(ErrorHandler.get())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError(error -> {
//                    Toast.makeText(this, R.string.parcel_detail_activity_no_connect_internet, Toast.LENGTH_SHORT).show();
//                })
//                .observeOn(Schedulers.io())
//                .doOnNext(this::saveParcelInfo)
                .subscribe(parcelInfoWithTrackingDetails -> {
                    Log.d(SWEET_TRACKER_TAG, "New update " + parcelInfoWithTrackingDetails.);
                });*/
                /*.onExceptionResumeNext(
                        repository.getStockUpdates()
                                .take(1)
                                .flatMap(Observable::fromIterable)
                )
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
                });*/
    }

    private void saveParcelInfo(ParcelInfoWithTrackingDetails parcelInfoWithTrackingDetails) {
        Log.d("APP", String.format("parcelInfo : %s", parcelInfoWithTrackingDetails.toString()));
        repository.insert(parcelInfoWithTrackingDetails.getParcelInfo());
    }
}