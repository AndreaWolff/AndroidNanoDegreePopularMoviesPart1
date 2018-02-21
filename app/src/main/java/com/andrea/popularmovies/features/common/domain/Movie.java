package com.andrea.popularmovies.features.common.domain;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

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

    protected Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readFloat();
        plotSynopsis = in.readString();
        posterPath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeFloat(voteAverage);
        dest.writeString(plotSynopsis);
        dest.writeString(posterPath);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

