package com.franciscoolivero.android.photoapp.di;

import com.franciscoolivero.android.photoapp.data.ApiService;
import com.franciscoolivero.android.photoapp.data.NetworkService;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    public ApiService provideApiService() {
        //Todo create an ApiService to provide
        return null;
    }

    @Provides
    public NetworkService networkService() {
        //Todo create a NetworkService to provide
        return  null;
    }
}
