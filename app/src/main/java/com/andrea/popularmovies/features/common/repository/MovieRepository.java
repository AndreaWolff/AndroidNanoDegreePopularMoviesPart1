package com.andrea.popularmovies.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.domain.Movie;

import java.util.List;

import io.reactivex.Single;

public interface MovieRepository {
    @NonNull Single<List<Movie>> getPopularMoviesList();

    @NonNull Single<List<Movie>> getTopRatedMovieList();
}
