package com.taein.sweettrackerassignment.parcelDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.taein.sweettrackerassignment.data.repository.ParcelInfoRepository;

public class ParcelDetailViewModel extends AndroidViewModel {

    private ParcelInfoRepository repository;

    public ParcelDetailViewModel(@NonNull Application app) {
        super(app);
        repository = ParcelInfoRepository.getInstance(app);
    }
}
