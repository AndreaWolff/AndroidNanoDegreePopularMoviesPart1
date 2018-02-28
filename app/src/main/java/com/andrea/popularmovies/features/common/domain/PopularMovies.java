package com.andrea.popularmovies.features.common.domain;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMovies {

    @SerializedName("results") @Expose private List<Movie> popularMoviesList = null;

    public PopularMovies(@NonNull List<Movie> popularMoviesList) {
        this.popularMoviesList = popularMoviesList;
    }

    @NonNull public List<Movie> getPopularMovieList() {
        return popularMoviesList;
    }
}