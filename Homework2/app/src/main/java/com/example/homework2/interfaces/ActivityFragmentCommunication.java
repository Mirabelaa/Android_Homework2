package com.example.homework2.interfaces;

import com.example.homework2.models.Album;
import com.example.homework2.models.User;

public interface ActivityFragmentCommunication {

    void openSecondFragment(User user);
    void openThirdFragment(Album album);
}
