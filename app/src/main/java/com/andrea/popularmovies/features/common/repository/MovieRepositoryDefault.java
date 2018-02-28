package com.andrea.popularmovies.features.common.repository;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.dao.MovieDao;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieRepositoryDefault implements MovieRepository {

    private final MovieDao movieDao;

    @Inject MovieRepositoryDefault(@NonNull MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @NonNull @Override public Single<PopularMovies> getPopularMoviesList() {
        return movieDao.getPopularMoviesList();
    }

    @NonNull @Override public Single<TopRatedMovies> getTopRatedMovieList() {
        return movieDao.getTopRatedMoviesList();
    }
}
