package com.franciscoolivero.android.photoapp.data;

import com.franciscoolivero.android.photoapp.model.AlbumModel;
import com.franciscoolivero.android.photoapp.model.PhotoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("albums")
    Single<List<AlbumModel>> getAlbums();

    @GET("albums/{album_id}/photos")
    Single<List<PhotoModel>> getPhotos(@Path(value = "album_id", encoded = true) String album_id);
}
