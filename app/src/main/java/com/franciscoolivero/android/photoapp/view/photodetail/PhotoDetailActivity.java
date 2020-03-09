package com.franciscoolivero.android.photoapp.view.photodetail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.utils.ImageUtil;

import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends AppCompatActivity {

    @BindView(R.id.photo_detail_image)
    ImageView photoImage;
    @BindView(R.id.photo_detail_album_title)
    TextView albumTitleText;
    @BindView(R.id.photo_detail_title)
    TextView photoTitleText;
    @BindView(R.id.photos_detail_toolbar)
    Toolbar toolbar;

    @InjectExtra
    PhotoModel photoModel;

    @InjectExtra
    String albumTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        Dart.inject(this);
        setupViews();
        setupActionToolBar();
    }

    private void setupActionToolBar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setTitle(photoModel.getPhotoTitle());
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void setupViews() {
        ImageUtil.loadImage(photoImage, photoModel.getPhotoUrl(), ImageUtil.getProgressDrawable(this));
        albumTitleText.setText(albumTitle);
        photoTitleText.setText(photoModel.getPhotoTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
