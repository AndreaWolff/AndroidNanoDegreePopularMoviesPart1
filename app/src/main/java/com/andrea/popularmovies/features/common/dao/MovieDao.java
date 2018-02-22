package com.andrea.popularmovies.features.common.dao;


import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import retrofit2.http.GET;
import rx.Observable;

public interface MovieDao {

    @GET("movie/popular?api_key=") Observable<PopularMovies> getPopularMoviesList();

    @GET("movie/top_rated?api_key=") Observable<TopRatedMovies> getTopRatedMoviesList();

}
