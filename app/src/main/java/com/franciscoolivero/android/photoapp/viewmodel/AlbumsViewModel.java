package com.franciscoolivero.android.photoapp.viewmodel;

import android.app.Activity;
import android.content.Intent;

import com.franciscoolivero.android.photoapp.data.NetworkService;
import com.franciscoolivero.android.photoapp.di.DaggerApiComponent;
import com.franciscoolivero.android.photoapp.model.AlbumModel;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.view.photos.Henson;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AlbumsViewModel extends ViewModel {

    public MutableLiveData<List<AlbumModel>> albumsLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> albumLoadErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> albumIsLoadingLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public AlbumsViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void fetchAlbums(Boolean isRefreshing) {
        if (!isRefreshing) {
            albumIsLoadingLiveData.setValue(true);
        }

        disposable.add(
                networkService.getAlbums()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<AlbumModel>>() {
                            @Override
                            public void onSuccess(List<AlbumModel> albumModels) {
                                albumsLiveData.setValue(albumModels);
                                albumLoadErrorLiveData.setValue(false);
                                albumIsLoadingLiveData.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                albumLoadErrorLiveData.setValue(true);
                                albumIsLoadingLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void fetchPhotos(int albumId, MutableLiveData<List<PhotoModel>> photosLiveData) {
        disposable.add(
                networkService.getPhotos(albumId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<PhotoModel>>() {
                            @Override
                            public void onSuccess(List<PhotoModel> photoModels) {
                                photosLiveData.setValue(photoModels);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void goToPhotosActivity(Activity sourceActivity, int albumId, String albumName) {
        Intent intent = Henson.with(sourceActivity)
                .gotoPhotosActivity()
                .albumId(albumId)
                .albumTitle(albumName)
                .build();
        sourceActivity.startActivity(intent);
        sourceActivity.finish();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
