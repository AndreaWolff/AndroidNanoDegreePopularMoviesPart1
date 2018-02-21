package com.andrea.popularmovies.features.common.domain;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    private static final String BASE_MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185";

    @SerializedName("id") @Expose private Integer id;
    @SerializedName("title") @Expose private String title;
    @SerializedName("release_date") @Expose private String releaseDate;
    @SerializedName("vote_average") @Expose private float voteAverage;
    @SerializedName("overview") @Expose private String plotSynopsis;
    @SerializedName("poster_path") @Expose private String posterPath;

    public Integer getId() {
        return id;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @NonNull public String getReleaseDate() {
        return releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    @NonNull public String getPlotSynopsis() {
        return plotSynopsis;
    }

    @NonNull public String getPosterPath() {
        return BASE_MOVIE_POSTER_URL + posterPath;
    }
}

