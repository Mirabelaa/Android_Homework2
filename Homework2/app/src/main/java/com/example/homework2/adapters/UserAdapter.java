package com.example.homework2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;
import com.example.homework2.interfaces.OnItemClick;
import com.example.homework2.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    ArrayList<User> users;
    OnItemClick onItemClick;

    public UserAdapter(ArrayList<User> users, OnItemClick onItemClick) {
        this.users = users;
        this.onItemClick = onItemClick;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
        boolean dropClick = users.get(position).arePostsVisible();
        holder.postsLayout.setVisibility(dropClick ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_cell, parent, false);
        return new UserViewHolder(view);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView posts;
        private final ConstraintLayout postsLayout;
        private final View view;
        private final ImageView dropButton;


        public UserViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.user_name);
            dropButton = view.findViewById(R.id.drop);
            posts = view.findViewById(R.id.post_title);
            this.view = view;
            postsLayout = view.findViewById(R.id.posts);
        }

        public void bind(User user) {
            name.setText(user.getName());
            StringBuilder userPosts = new StringBuilder();
            for (int index = 0; index < user.getPosts().size(); index++) {
                String post = "";
                post += user.getPosts().get(index).getTitle() + "\n";
                userPosts.append(post);
            }
            posts.setText(userPosts.toString());
            view.setOnClickListener(v -> {
                onItemClick.onUserClick(user);
                notifyItemChanged(getAdapterPosition());
            });
            dropButton.setOnClickListener(v -> {
                user.setArePostsVisible(!user.arePostsVisible());

                onItemClick.onArrowClick(user);
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
