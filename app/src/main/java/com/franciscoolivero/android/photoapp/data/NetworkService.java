package com.franciscoolivero.android.photoapp.data;

import com.franciscoolivero.android.photoapp.di.DaggerApiComponent;
import com.franciscoolivero.android.photoapp.model.AlbumModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class NetworkService {

    private static NetworkService instance;

    @Inject
    public ApiService api;

    private NetworkService() {
        DaggerApiComponent.create().inject(this);
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public Single<List<AlbumModel>> getAlbums() {
        return api.getAlbums();
    }

}
