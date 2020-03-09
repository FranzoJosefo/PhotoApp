package com.franciscoolivero.android.photoapp.di;

import com.franciscoolivero.android.photoapp.data.ApiService;
import com.franciscoolivero.android.photoapp.data.NetworkService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Provides
    public ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Provides
    public NetworkService provideNetworkService() {
        return NetworkService.getInstance();
    }

}
