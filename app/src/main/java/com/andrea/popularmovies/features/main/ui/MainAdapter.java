package com.andrea.popularmovies.features.main.ui;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.bumptech.glide.Glide;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private ListItemClickListener onClickListener;
    private List<String> moviePosterPath;

    interface ListItemClickListener {
        void onListItemClick(int listItem);
    }

    public MainAdapter(@NonNull ListItemClickListener onClickListener,
                       @NonNull List<String> moviePosterPath) {
        this.onClickListener = onClickListener;
        this.moviePosterPath = moviePosterPath;
    }

    @Override public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.movie_poster_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override public int getItemCount() {
        return !moviePosterPath.isEmpty() && moviePosterPath.size() > 0 ? moviePosterPath.size() : 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView moviePosterImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            moviePosterImageView = itemView.findViewById(R.id.imageview_movie_poster_list_item);
            itemView.setOnClickListener(this);
        }

        void bind(int listItem) {
            Glide.with(MovieApplication.getDagger().getContext())
                 .load(moviePosterPath.get(listItem))
                 .into(moviePosterImageView);
        }

        @Override public void onClick(View view) {
            onClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
