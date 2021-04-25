package com.example.homework2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.homework2.R;
import com.example.homework2.VolleyConfigSingleton;
import com.example.homework2.models.Picture;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    ArrayList<Picture> images;
    public ImageAdapter(ArrayList<Picture> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        android.view.View view = inflater.inflate(R.layout.image_cell, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Picture image = images.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageV;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageV = itemView.findViewById(R.id.image_album);
        }

        public void bind(Picture image) {
            String imageViewUrl=image.getUrl();
            ImageLoader imageLoader= VolleyConfigSingleton.getInstance(imageV.getContext()).getImageLoader();

            imageLoader.get(imageViewUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageV.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

}
