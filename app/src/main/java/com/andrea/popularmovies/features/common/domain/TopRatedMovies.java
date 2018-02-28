package com.andrea.popularmovies.features.common.domain;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRatedMovies {

    @SerializedName("results") @Expose private List<Movie> topRatedMoviesList = null;

    public TopRatedMovies(@NonNull List<Movie> topRatedMoviesList) {
        this.topRatedMoviesList = topRatedMoviesList;
    }

    @NonNull public List<Movie> getTopRatedMoviesList() {
        return topRatedMoviesList;
    }
}
