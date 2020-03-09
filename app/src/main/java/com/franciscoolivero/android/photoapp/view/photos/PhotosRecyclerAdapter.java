package com.franciscoolivero.android.photoapp.view.photos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.franciscoolivero.android.photoapp.R;
import com.franciscoolivero.android.photoapp.model.PhotoModel;
import com.franciscoolivero.android.photoapp.utils.ImageUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.PhotosViewHolder> {

    private List<PhotoModel> photosList;
    private Context context;

    public PhotosRecyclerAdapter(List<PhotoModel> photosList, Context context) {
        this.photosList = photosList;
        this.context = context;
    }

    public void updatePhotos(List<PhotoModel> newPhotos) {
        photosList.clear();
        photosList.addAll(newPhotos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_photos_page, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        holder.bind(photosList.get(position));
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_cell_photo_image_view)
        ImageView photoImage;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PhotoModel photoModel) {
            ImageUtil.loadImage(photoImage, photoModel.getPhotoThumbnailUrl(), ImageUtil.getProgressDrawable(photoImage.getContext()));
            itemView.setOnClickListener(view -> ((PhotosActivity) context).goToPhotoDetailActivity(photoModel));
        }
    }
}
