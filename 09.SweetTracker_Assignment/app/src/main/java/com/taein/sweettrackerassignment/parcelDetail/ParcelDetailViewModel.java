package com.taein.sweettrackerassignment.parcelDetail;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.taein.sweettrackerassignment.data.entity.ParcelInfo;
import com.taein.sweettrackerassignment.data.entity.TrackingDetail;
import com.taein.sweettrackerassignment.data.repository.ParcelInfoRepository;
import com.taein.sweettrackerassignment.data.vo.ParcelInfoWithTrackingDetailsVO;
import com.taein.sweettrackerassignment.utils.ErrorHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;

import static com.taein.sweettrackerassignment.parcelDetail.ParcelDetailActivity.SWEET_TRACKER_TAG;

@Data
public class ParcelDetailViewModel extends ViewModel {

    private ParcelInfoRepository repository;
    private MutableLiveData<ParcelInfoWithTrackingDetailsVO> parcelInfoWithTrackingDetailsLiveData = new MutableLiveData<>();

    public ParcelDetailViewModel(@NonNull ParcelInfoRepository repository) {
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void onStart(){
        repository.getParcelVOFromQuery()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parcelInfoWithTrackingDetailsVO -> {
                    Log.d(SWEET_TRACKER_TAG, "New update time : "
                            + parcelInfoWithTrackingDetailsVO.getParcelCompanyName());
                    parcelInfoWithTrackingDetailsLiveData.setValue(parcelInfoWithTrackingDetailsVO);
                });
    }

    /*repository = ParcelInfoRepository.getInstance(getApplication());
        repository.getParcelVOFromQuery()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parcelInfoWithTrackingDetail -> {
                    Log.d(SWEET_TRACKER_TAG, "New update time : " + parcelInfoWithTrackingDetail.getParcelInfo().getParcelCompanyName());
                    trackingDetailDataAdapter.addAll(parcelInfoWithTrackingDetail.getTrackingDetails());
                    binding.setParcelInfo(parcelInfoWithTrackingDetail.getParcelInfo());
                });*/

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
