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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework2.R;
import com.example.homework2.adapters.UserAdapter;
import com.example.homework2.interfaces.ActivityFragmentCommunication;
import com.example.homework2.interfaces.OnItemClick;
import com.example.homework2.models.Album;
import com.example.homework2.models.Post;
import com.example.homework2.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.homework2.constants.Constants.BASE_URL;
import static com.example.homework2.constants.Constants.BODY;
import static com.example.homework2.constants.Constants.ID;
import static com.example.homework2.constants.Constants.NAME;
import static com.example.homework2.constants.Constants.TITLE;
import static com.example.homework2.constants.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ActivityFragmentCommunication activityFragmentCommunication;
    private ArrayList<User> users = new ArrayList<>();
    private UserAdapter userAdapter = new UserAdapter(users, new OnItemClick() {
        @Override
        public void onUserClick(User user) {
            activityFragmentCommunication.openSecondFragment(user);

        }

        @Override
        public void onAlbumClick(Album album) {

        }

        @Override
        public void onArrowClick(User user) {

            getPosts(user);
        }

    });

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        getUsers();
    }


    public void setUpRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        setUpRecyclerView(view);


        return view;
    }

    public void getUsers() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BASE_URL;
        url+="/users";
        StringRequest getUsersRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handleUserResponse(response);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },

                error -> Toast.makeText(getContext(), "ERROR: get users failed with error", Toast.LENGTH_LONG).show()

        );
        queue.add(getUsersRequest);
    }

    public void getPosts(User user) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = BASE_URL;
        url += "/users/" + user.getId() + "/posts";
        StringRequest getPostsRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handlePostResponse(response, user);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "ERROR: get posts failed with error", Toast.LENGTH_LONG).show()
        );
        queue.add(getPostsRequest);
    }

    public void handleUserResponse(String response) throws JSONException {

        JSONArray userJsonArray = new JSONArray(response);

        for (int index = 0; index < userJsonArray.length(); index++) {

            JSONObject userObject = userJsonArray.getJSONObject(index);
            String name = userObject.getString(NAME);
            int id = userObject.getInt(ID);
            User user = new User(name, id);

            users.add(user);
        }
        userAdapter.notifyDataSetChanged();
    }

    public void handlePostResponse(String response, User user) throws JSONException {

        user.getPosts().clear();
        JSONArray postsJsonArray = new JSONArray(response);
        for (int index = 0; index < postsJsonArray.length(); index++) {
            JSONObject postObject = postsJsonArray.getJSONObject(index);

            if (postObject != null) {
                int userId = postObject.getInt(USER_ID);
                int id = postObject.getInt(ID);
                String title = postObject.getString(TITLE);
                String body = postObject.getString(BODY);
                Post post = new Post(userId, id, title, body);
                if (!user.getPosts().contains(post))
                    user.getPosts().add(post);
            }
        }
        userAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }


    }
}