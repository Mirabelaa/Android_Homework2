package com.example.homework2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework2.R;
import com.example.homework2.adapters.ImageAdapter;
import com.example.homework2.models.Album;
import com.example.homework2.models.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.homework2.constants.Constants.ALBUM_ID;
import static com.example.homework2.constants.Constants.BASE_URL;
import static com.example.homework2.constants.Constants.ID;
import static com.example.homework2.constants.Constants.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Album album;
    private static ArrayList<Picture> pictures =new ArrayList<Picture>();
    private ImageAdapter imageAdapter=new ImageAdapter(pictures);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment(Album album) {
        this.album=album;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2, Album album) {
        ThirdFragment fragment = new ThirdFragment(album);
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
        getImages(this.album);

    }

    public void setUpRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.image_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);
        setUpRecyclerView(view);
        return view;
    }

    public void getImages(Album album)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BASE_URL;
        url += "/albums/" + album.getId() + "/photos";
        StringRequest getImagesRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handleImagesResponse(response);

                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "ERROR: get albums failed with error", Toast.LENGTH_SHORT).show()
        );
        queue.add(getImagesRequest);
    }

    public void handleImagesResponse(String response) throws JSONException {
        pictures.clear();
        JSONArray imagesJsonArray = new JSONArray(response);
        for (int index = 0; index < imagesJsonArray.length(); index++) {
            JSONObject imageObject = imagesJsonArray.getJSONObject(index);

            if (imageObject != null) {
                int albumId=imageObject.getInt(ALBUM_ID);
                int id=imageObject.getInt(ID);
                String url = imageObject.getString(URL);
                url+=".png";
                Picture image = new Picture(albumId, id, url);
                pictures.add(image);
            }
        }
        imageAdapter.notifyDataSetChanged();
    }

}