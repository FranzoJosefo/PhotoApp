package com.franciscoolivero.android.photoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PhotoModel implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.albumId);
        dest.writeString(this.photoTitle);
        dest.writeString(this.photoThumbnailUrl);
        dest.writeString(this.photoUrl);
    }

    protected PhotoModel(Parcel in) {
        this.albumId = in.readInt();
        this.photoTitle = in.readString();
        this.photoThumbnailUrl = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Parcelable.Creator<PhotoModel> CREATOR = new Parcelable.Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel source) {
            return new PhotoModel(source);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };
}


