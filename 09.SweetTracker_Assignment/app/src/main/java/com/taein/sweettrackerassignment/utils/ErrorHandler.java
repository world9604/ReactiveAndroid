package com.taein.sweettrackerassignment.utils;

import android.util.Log;
import io.reactivex.functions.Consumer;

import static com.taein.sweettrackerassignment.parcelDetail.ParcelDetailActivity.SWEET_TRACKER_TAG;


public class ErrorHandler implements Consumer<Throwable> {

    private static final ErrorHandler INSTANCE = new ErrorHandler();

    public static ErrorHandler get() {
        return INSTANCE;
    }

    private ErrorHandler() {}

    @Override
    public void accept(Throwable throwable) {
        Log.e(SWEET_TRACKER_TAG, "Error on " + Thread.currentThread().getName() + ":", throwable);
    }
}
