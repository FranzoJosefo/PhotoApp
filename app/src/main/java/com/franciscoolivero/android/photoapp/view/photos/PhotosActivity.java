package com.franciscoolivero.android.photoapp.view.photos;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.dart.InjectExtra;
import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.viewmodel.PhotosViewModel;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosActivity extends AppCompatActivity {

    @BindView(R.id.photos_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.photos_toolbar)
    Toolbar toolbar;

    @BindView(R.id.photos_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.photos_error_text_view)
    TextView photosLoadingError;

    @BindView(R.id.photos_progress_spinner)
    ProgressBar progressSpinner;

    @InjectExtra
    int albumId;

    private PhotosViewModel photosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        photosViewModel.fetchPhotos(albumId);
    }
}
