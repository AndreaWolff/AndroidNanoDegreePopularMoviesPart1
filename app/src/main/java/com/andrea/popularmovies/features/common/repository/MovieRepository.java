package com.andrea.popularmovies.features.common.repository;


import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import rx.Observable;

public interface MovieRepository {
    Observable<PopularMovies> getPopularMoviesList();

    Observable<TopRatedMovies> getTopRatedMovieList();
}
