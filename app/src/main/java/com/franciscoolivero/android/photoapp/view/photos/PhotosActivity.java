package com.franciscoolivero.android.photoapp.view.photos;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.viewmodel.PhotosViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosActivity extends AppCompatActivity {

    private static final int RECYCLER_GRID_COLUMNS = 3;

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

    @BindView(R.id.photos_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectExtra
    int albumId;

    @InjectExtra
    String albumTitle;

    private PhotosViewModel photosViewModel;

    private PhotosRecyclerAdapter photosRecyclerAdapter = new PhotosRecyclerAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        Dart.inject(this);

        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        photosViewModel.fetchPhotos(albumId, false);

        setupActionToolBar();
        setupRecyclerView();
        setupSwipeRefreshListener();
        setupObserversViewModel();
    }

    private void setupActionToolBar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(albumTitle);
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, RECYCLER_GRID_COLUMNS));
        recyclerView.setAdapter(photosRecyclerAdapter);
    }

    private void setupSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photosViewModel.fetchPhotos(albumId, true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupObserversViewModel() {

        photosViewModel.photoIsLoadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    progressSpinner.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        photosLoadingError.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        photosViewModel.photoLoadErrorLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) {
                    photosLoadingError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }

            }
        });

        photosViewModel.photosLiveData.observe(this, new Observer<List<PhotoModel>>() {
            @Override
            public void onChanged(List<PhotoModel> photoModels) {
                if (photoModels != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    photosRecyclerAdapter.updatePhotos(photoModels);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        photosViewModel.goToAlbumActivity(this);
        return false;
    }
}
