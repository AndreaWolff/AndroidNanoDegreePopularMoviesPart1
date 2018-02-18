package com.andrea.popularmovies.features.main.ui;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.bumptech.glide.Glide;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private ListItemClickListener onClickListener;
    private Movie movie;
    private int numOfItems;

    interface ListItemClickListener {
        void onListItemClick(int listItem);
    }

    public MainAdapter(
            @NonNull ListItemClickListener onClickListener, @NonNull Movie movie, int numOfItems) {
        this.onClickListener = onClickListener;
        this.movie = movie;
        this.numOfItems = numOfItems;
    }

    @Override public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override public int getItemCount() {
        return numOfItems > 0 ? numOfItems : 0;
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
                 .load(movie.getPosterPath())
                 .into(moviePosterImageView);
        }

        @Override public void onClick(View view) {
            onClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
