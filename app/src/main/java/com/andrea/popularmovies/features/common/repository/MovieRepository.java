package com.andrea.popularmovies.features.common.repository;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import io.reactivex.Single;

public interface MovieRepository {
    @NonNull Single<PopularMovies> getPopularMoviesList();

    @NonNull Single<TopRatedMovies> getTopRatedMovieList();
}
