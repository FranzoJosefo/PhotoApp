package com.franciscoolivero.android.photoapp.di;

import com.franciscoolivero.android.photoapp.data.ApiService;
import com.franciscoolivero.android.photoapp.data.NetworkService;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(NetworkService networkService);

    void inject(ApiService apiService);
}
