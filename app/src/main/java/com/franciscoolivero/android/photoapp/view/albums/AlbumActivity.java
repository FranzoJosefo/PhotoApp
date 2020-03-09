package com.franciscoolivero.android.photoapp.view.albums;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.model.AlbumModel;
import com.franciscoolivero.android.photoapp.viewmodel.AlbumsViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.album_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.albums_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.albums_toolbar)
    Toolbar toolbar;

    @BindView(R.id.albums_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.albums_error_text_view)
    TextView albumLoadingError;

    @BindView(R.id.albums_progress_spinner)
    ProgressBar progressSpinner;

    private AlbumsRecyclerAdapter albumsRecyclerAdapter = new AlbumsRecyclerAdapter(new ArrayList<>(), this);

    private AlbumsViewModel albumsViewModel;

    private static final int RECYCLER_GRID_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
        albumsViewModel.fetchAlbums();

        setupRecyclerView();
        setupSwipeRefreshListener();
        setupObserversViewModel();

    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, RECYCLER_GRID_COLUMNS));
        recyclerView.setAdapter(albumsRecyclerAdapter);
    }

    private void setupSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                albumsViewModel.fetchAlbums();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupObserversViewModel() {

        albumsViewModel.albumIsLoadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    progressSpinner.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        albumLoadingError.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        albumsViewModel.albumLoadErrorLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) {
                    albumLoadingError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }

            }
        });

        albumsViewModel.albumsLiveData.observe(this, new Observer<List<AlbumModel>>() {
            @Override
            public void onChanged(List<AlbumModel> albumModels) {
                if (albumModels != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    albumsRecyclerAdapter.updateAlbums(albumModels);
                }
            }
        });
    }

    public AlbumsViewModel getViewModel() {
        return albumsViewModel;
    }
}
