package com.andrea.popularmovies.features.common.repository;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieDao {

    @GET("movie/popular?api_key=") Single<MoviesDto> getPopularMoviesList();

    @GET("movie/top_rated?api_key=") Single<MoviesDto> getTopRatedMoviesList();

}
