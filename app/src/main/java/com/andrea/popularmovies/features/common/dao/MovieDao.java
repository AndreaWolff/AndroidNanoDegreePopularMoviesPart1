package com.andrea.popularmovies.features.common.dao;


import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieDao {

    @GET("movie/popular?api_key=") Single<PopularMovies> getPopularMoviesList();

    @GET("movie/top_rated?api_key=") Single<TopRatedMovies> getTopRatedMoviesList();

}
