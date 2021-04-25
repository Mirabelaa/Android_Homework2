package com.example.homework2.interfaces;

import com.example.homework2.models.Album;
import com.example.homework2.models.User;

public interface OnItemClick {
    void onUserClick(User user);
    void onAlbumClick(Album album);
    void onArrowClick(User user);
}
