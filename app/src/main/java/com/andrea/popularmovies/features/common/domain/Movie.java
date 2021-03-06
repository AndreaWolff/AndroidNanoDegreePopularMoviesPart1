package com.andrea.popularmovies.features.common.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Movie implements Parcelable {

    private static final String BASE_MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185";

    private final String title;
    private final String releaseDate;
    private final float voteAverage;
    private final String plotSynopsis;
    private final String posterPath;
    private final String backdropPhotoPath;

    public Movie(@NonNull String title,
                 @NonNull String releaseDate,
                 float voteAverage,
                 @NonNull String plotSynopsis,
                 @NonNull String posterPath,
                 @NonNull String backdropPhotoPath) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
        this.posterPath = posterPath;
        this.backdropPhotoPath = backdropPhotoPath;
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

    @NonNull public String getBackdropPhotoPath() { return BASE_MOVIE_POSTER_URL + backdropPhotoPath; }

    // Parcelable code generated from http://www.parcelabler.com/
    private Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readFloat();
        plotSynopsis = in.readString();
        posterPath = in.readString();
        backdropPhotoPath = in.readString();
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
        dest.writeString(backdropPhotoPath);
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

