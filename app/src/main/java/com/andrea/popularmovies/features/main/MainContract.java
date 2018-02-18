package com.andrea.popularmovies.features.main;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.domain.Movie;

public interface MainContract {
    interface View {
        void renderPopularMoviesTitle(@NonNull String title);

        void showPopularMovies(@NonNull Movie movie);

        void showError(@NonNull String message);

        void navigateToMovieDetails();
    }

    interface Presenter {
        void loadPopularMovies();
    }
}
