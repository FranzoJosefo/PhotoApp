package com.franciscoolivero.android.photoapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.model.AlbumModel;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.utils.ImageUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsRecyclerAdapter extends RecyclerView.Adapter<AlbumsRecyclerAdapter.AlbumViewHolder> {

    private List<AlbumModel> albumsList;

    private Context context;

    public AlbumsRecyclerAdapter(List<AlbumModel> albumsList, Context context) {
        this.albumsList = albumsList;
        this.context = context;
    }

    public void updateAlbums(List<AlbumModel> newAlbums) {
        albumsList.clear();
        albumsList.addAll(newAlbums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell_albums_page, parent, false);
        return new AlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bind(albumsList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_cell_album_cover_image_view)
        ImageView albumCoverImage;
        @BindView(R.id.grid_cell_album_name_text_view)
        TextView albumName;

        public MutableLiveData<List<PhotoModel>> photoModels = new MutableLiveData<>();

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setupObservers();
        }

        private void setupObservers() {
            photoModels.observe((MainActivity) context, photoModels -> ImageUtil.loadImage(
                    albumCoverImage, photoModels.get(0).getPhotoThumbnailUrl(), ImageUtil.getProgressDrawable(albumCoverImage.getContext())));
        }

        public void bind(AlbumModel album) {
            ((MainActivity) context).getViewModel().fetchPhotos(album.getAlbum_id(), photoModels);
            albumName.setText(album.getAlbum_title());
        }
    }
}
