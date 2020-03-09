package com.franciscoolivero.android.photoapp.di;

import com.franciscoolivero.android.photoapp.data.NetworkService;
import com.franciscoolivero.android.photoapp.viewmodel.AlbumsViewModel;
import com.franciscoolivero.android.photoapp.viewmodel.PhotosViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(NetworkService networkService);

    void inject(AlbumsViewModel albumsViewModel);

    void inject(PhotosViewModel photosViewModel);
}
