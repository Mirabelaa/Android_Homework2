package com.example.homework2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;
import com.example.homework2.interfaces.OnItemClick;
import com.example.homework2.models.Album;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    ArrayList<Album> albums;
    OnItemClick onItemClick;
    public AlbumAdapter(ArrayList<Album> albums, OnItemClick onItemClick) {
        this.albums = albums;
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.album_cell,parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {

        Album album = albums.get(position);
        holder.bind(album);
    }

    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView album;

        public AlbumViewHolder(View view) {
            super(view);
            this.album = view.findViewById(R.id.album_name);
            this.view = view;
        }

        public void bind(Album album) {

            this.album.setText(album.getName());
            view.setOnClickListener(v -> {
                onItemClick.onAlbumClick(album);
                notifyItemChanged(getAdapterPosition());
            });
        }

    }

}
