package com.franciscoolivero.android.photoapp.model;

import com.google.gson.annotations.SerializedName;

public class AlbumModel {
    @SerializedName("id")
    private int albumId;
    @SerializedName("title")
    private String albumTitle;

    public AlbumModel(int album_id, String album_title) {
        this.albumId = album_id;
        this.albumTitle = album_title;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

}
