package com.example.homework2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework2.R;
import com.example.homework2.adapters.AlbumAdapter;
import com.example.homework2.interfaces.ActivityFragmentCommunication;
import com.example.homework2.interfaces.OnItemClick;
import com.example.homework2.models.Album;
import com.example.homework2.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.homework2.constants.Constants.BASE_URL;
import static com.example.homework2.constants.Constants.ID;
import static com.example.homework2.constants.Constants.TITLE;
import static com.example.homework2.constants.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ActivityFragmentCommunication fragmentCommunication;
    private User user;
    private ArrayList<Album> albums = new ArrayList<>();
    private AlbumAdapter albumAdapter = new AlbumAdapter(albums, new OnItemClick() {
        @Override
        public void onUserClick(User user) {
        }

        @Override
        public void onAlbumClick(Album album) {

            fragmentCommunication.openThirdFragment(album);
        }

        @Override
        public void onArrowClick(User user) {

        }
    });
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment(User user) {
        // Required empty public constructor
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2, User user) {
        SecondFragment fragment = new SecondFragment(user);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getAlbums(this.user);
    }


    public void setUpRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.albums_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(albumAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        setUpRecyclerView(view);

        return view;
    }

    public void getAlbums(User user) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BASE_URL;
        url += "/users/" + user.getId() + "/albums";

        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handleAlbumResponse(response);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "ERROR: get albums failed with error", Toast.LENGTH_LONG).show()
        );
        queue.add(getAlbumsRequest);
    }

    public void handleAlbumResponse(String response) throws JSONException {

        albums.clear();
        JSONArray albumsJsonArray = new JSONArray(response);
        for (int index = 0; index < albumsJsonArray.length(); index++) {
            JSONObject albumObject = albumsJsonArray.getJSONObject(index);

            if (albumObject != null) {
                int userId = albumObject.getInt(USER_ID);
                int id = albumObject.getInt(ID);
                String title = albumObject.getString(TITLE);
                Album album = new Album(userId, id, title);
                if (!albums.contains(album))
                    albums.add(album);
            }
        }
        albumAdapter.notifyDataSetChanged();
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ActivityFragmentCommunication) {
            fragmentCommunication = (ActivityFragmentCommunication) context;
        }


    }

}