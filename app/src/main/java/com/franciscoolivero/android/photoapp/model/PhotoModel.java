package com.franciscoolivero.android.photoapp.model;

import com.google.gson.annotations.SerializedName;

public class PhotoModel {
    private int albumId;
    @SerializedName("title")
    private String photoTitle;
    @SerializedName("thumbnailUrl")
    private String photoThumbnailUrl;
    @SerializedName("url")
    private String photoUrl;

    public PhotoModel(int albumId, String photoTitle, String photoThumbnailUrl, String photoUrl) {
        this.albumId = albumId;
        this.photoTitle = photoTitle;
        this.photoThumbnailUrl = photoThumbnailUrl;
        this.photoUrl = photoUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public String getPhotoThumbnailUrl() {
        return photoThumbnailUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}


