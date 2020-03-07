package com.franciscoolivero.android.photoapp.model;

import com.google.gson.annotations.SerializedName;

public class AlbumModel {
    @SerializedName("id")
    private int album_id;
    @SerializedName("title")
    private String album_title;

    public AlbumModel(int album_id, String album_title) {
        this.album_id = album_id;
        this.album_title = album_title;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public String getAlbum_title() {
        return album_title;
    }

}
