package com.taein.sweettrackerassignment.utils;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.taein.sweettrackerassignment.data.repository.ParcelInfoRepository;
import com.taein.sweettrackerassignment.parcelDetail.ParcelDetailViewModel;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;
    private ParcelInfoRepository repository;

    private ViewModelFactory(ParcelInfoRepository repository) {
        this.repository = repository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    // TODO: Version 2.0에서 수행할 것.
                    /*INSTANCE = new ViewModelFactory(
                            Injection.provideTasksRepository(application.getApplicationContext()));*/
                    // INSTANCE = new ViewModelFactory(application);
                    INSTANCE = new ViewModelFactory(ParcelInfoRepository.getInstance(application));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ParcelDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new ParcelDetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
