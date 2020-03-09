package com.franciscoolivero.android.photoapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.franciscoolivero.android.photoapp.R;
import com.squareup.picasso.Picasso;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class ImageUtil {

    public static void loadImage(ImageView view, String url, CircularProgressDrawable progressDrawable) {
        Picasso.get()
                .load(url)
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher_round)
                .into(view);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }

}
