package com.andrea.popularmovies.features.main;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.domain.PopularMovies;

public interface MainContract {
    interface View {
        void renderPopularMoviesTitle(@NonNull String title);

        void showPopularMovies(@NonNull PopularMovies movie);

        void showError(@NonNull String message);

        void navigateToMovieDetails(int listItem);
    }

    interface Presenter {
        void loadPopularMovies();
    }
}
