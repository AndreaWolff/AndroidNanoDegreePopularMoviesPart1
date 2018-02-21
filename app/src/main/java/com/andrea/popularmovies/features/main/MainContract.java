package com.andrea.popularmovies.features.main;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

public interface MainContract {
    interface View {
        void renderPopularMoviesTitle(@NonNull String title);

        void renderTopRatedMoviesTitle(@NonNull String title);

        void showPopularMovies(@NonNull PopularMovies movie);

        void showTopRatedMovies(@NonNull TopRatedMovies topRatedMovies);

        void showError(@NonNull String message);

        void navigateToMovieDetails(int listItem);
    }

    interface Presenter {

    }
}
