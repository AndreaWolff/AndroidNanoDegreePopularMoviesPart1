package com.andrea.popularmovies.features.common.repository;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.dao.MovieDao;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import javax.inject.Inject;

import rx.Observable;

public class MovieRepositoryDefault implements MovieRepository {

    private final MovieDao movieDao;

    @Inject MovieRepositoryDefault(@NonNull MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override public Observable<PopularMovies> getPopularMoviesList() {
        return movieDao.getPopularMoviesList();
    }

    @Override public Observable<TopRatedMovies> getTopRatedMovieList() {
        return movieDao.getTopRatedMoviesList();
    }
}
