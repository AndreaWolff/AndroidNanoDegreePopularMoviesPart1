package com.andrea.popularmovies.features.main;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.andrea.popularmovies.features.common.domain.Movie;

import java.util.List;

public interface MainContract {
    interface View {
        void renderPopularMoviesTitle(@NonNull String title);

        void renderTopRatedMoviesTitle(@NonNull String title);

        void showMoviesList(@NonNull List<Movie> movieList);

        void showError(@NonNull AlertDialog.Builder builder);

        void showProgressBar();

        void hideProgressBar();

        void navigateToMovieDetails(@NonNull Intent intent);
    }

    interface Presenter {

    }
}
