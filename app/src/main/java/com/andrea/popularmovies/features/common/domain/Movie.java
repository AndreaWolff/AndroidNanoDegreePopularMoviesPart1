package com.andrea.popularmovies.features.common.domain;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("title") private String title;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("vote_average") private String voteAverage;
    @SerializedName("overview") private String plotSynopsis;
    @SerializedName("poster_path") private String posterPath;

    public Movie(@NonNull String title,
                 @NonNull String releaseDate,
                 @NonNull String voteAverage,
                 @NonNull String plotSynopsis,
                 @NonNull String posterPath) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
        this.posterPath = posterPath;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @NonNull public String getReleaseDate() {
        return releaseDate;
    }

    @NonNull public String getVoteAverage() {
        return voteAverage;
    }

    @NonNull public String getPlotSynopsis() {
        return plotSynopsis;
    }

    @NonNull public String getPosterPath() {
        String BASE_MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
        return BASE_MOVIE_POSTER_URL + posterPath;
    }
}
