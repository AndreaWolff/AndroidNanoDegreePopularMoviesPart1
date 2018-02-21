package com.andrea.popularmovies.features.common.domain;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRatedMovies {

    @SerializedName("results") @Expose private List<Movie> topRatedMoviesList = null;

    @NonNull public List<Movie> getTopRatedMoviesList() {
        return topRatedMoviesList;
    }
}
