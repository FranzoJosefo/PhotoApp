package com.franciscoolivero.android.photoapp.viewmodel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlbumsViewModel extends ViewModel {

    public MutableLiveData<List<AlbumsViewModel>> albumLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> albumLoadErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> albumIsLoadingLiveData = new MutableLiveData<>();
}
