package com.franciscoolivero.android.photoapp.viewmodel;

import android.app.Activity;
import android.content.Intent;

import com.franciscoolivero.android.photoapp.data.NetworkService;
import com.franciscoolivero.android.photoapp.di.DaggerApiComponent;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.view.albums.AlbumActivity;
import com.franciscoolivero.android.photoapp.view.photo.Henson;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PhotosViewModel extends ViewModel {

    public MutableLiveData<List<PhotoModel>> photosLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> photoLoadErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> photoIsLoadingLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public PhotosViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void fetchPhotos(int albumId, Boolean isRefreshing) {
        if (!isRefreshing) {
            photoIsLoadingLiveData.setValue(true);
        }
        disposable.add(
                networkService.getPhotos(albumId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<PhotoModel>>() {
                            @Override
                            public void onSuccess(List<PhotoModel> photoModels) {
                                photoIsLoadingLiveData.setValue(false);
                                photoLoadErrorLiveData.setValue(false);
                                photosLiveData.setValue(photoModels);
                            }

                            @Override
                            public void onError(Throwable e) {
                                photoIsLoadingLiveData.setValue(false);
                                photoLoadErrorLiveData.setValue(true);
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void goToAlbumActivity(Activity sourceActivity) {
        Intent intent = new Intent(sourceActivity, AlbumActivity.class);
        sourceActivity.startActivity(intent);
        sourceActivity.finish();
    }

    public void goToPhotoDetailActivity(Activity sourceActivity, PhotoModel photo, String albumTitle) {
        Intent intent = Henson.with(sourceActivity)
                .gotoPhotoDetailActivity()
                .albumTitle(albumTitle)
                .photoModel(photo)
                .build();
        sourceActivity.startActivity(intent);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
