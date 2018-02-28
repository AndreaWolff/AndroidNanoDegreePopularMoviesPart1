package com.andrea.popularmovies.features.main.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;
import com.andrea.popularmovies.features.common.repository.MovieRepository;
import com.andrea.popularmovies.features.details.ui.DetailsActivity;
import com.andrea.popularmovies.features.main.MainContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_BACKDROP_PHOTO;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

public class MainPresenter implements MainContract.Presenter {

    private static final String MOVIE_LIST = "MAPML";

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MovieRepository movieRepository;
    private final Context context;

    private MainContract.View view;
    private List<Movie> movieList;

    @Inject MainPresenter(@NonNull MovieRepository movieRepository,
                          @NonNull Context context) {
        this.movieRepository = movieRepository;
        this.context = context;
    }

    public void connectView(@NonNull MainContract.View view, @Nullable Bundle savedInstanceState) {
        this.view = view;

        init();

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST);

            if (movieList != null) {
                view.showMoviesList(movieList);
                return;
            }
        }

        loadPopularMovies();

    }

    private void init() {
        if (view != null) {
            view.renderPopularMoviesTitle(context.getString(R.string.main_popular_movies_title));
        }
    }

    public void onSavedInstanceState(Bundle outState) {
        if (movieList != null) {
            outState.putParcelableArrayList(MOVIE_LIST, new ArrayList<Parcelable>(movieList));
        }
    }

    public void disconnectView() {
        disposable.clear();
        view = null;
    }

    public void loadPopularMovies() {
        if (view != null) {
            view.showProgressBar();
        }

        disposable.add(movieRepository.getPopularMoviesList()
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(this::handlePopularMoviesResponseSuccessful, this::handleResponseError));
    }

    public void loadTopRatedMovies() {
        if (view != null) {
            view.showProgressBar();
        }

        disposable.add(movieRepository.getTopRatedMovieList()
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(this::handleTopRatedMoviesResponseSuccessful, this::handleResponseError));
    }

    private void handlePopularMoviesResponseSuccessful(PopularMovies popularMovies) {
        movieList = new ArrayList<>(popularMovies.getPopularMovieList());

        if (view != null) {
            view.hideProgressBar();
            view.renderPopularMoviesTitle(context.getString(R.string.main_popular_movies_title));
            view.showMoviesList(movieList);
        }
    }

    private void handleTopRatedMoviesResponseSuccessful(TopRatedMovies topRatedMovies) {
        movieList = new ArrayList<>(topRatedMovies.getTopRatedMoviesList());

        if (view != null) {
            view.hideProgressBar();
            view.renderTopRatedMoviesTitle(context.getString(R.string.main_top_rated_movies_title));
            view.showMoviesList(movieList);
        }
    }

    private void handleResponseError(Throwable error) {
        if (view != null) {
            view.hideProgressBarOnMovieListError();
            view.showError(error.getMessage());
        }
    }

    public void onMoviePosterSelected(int listItem) {
        if (view != null) {
            Intent intent = new Intent(context, DetailsActivity.class);
            Movie movie = movieList.get(listItem);
            intent.putExtra(MOVIE_TITLE, movie.getTitle());
            intent.putExtra(MOVIE_RELEASE_DATE, movie.getReleaseDate());
            intent.putExtra(MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
            intent.putExtra(MOVIE_PLOT_SYNOPSIS, movie.getPlotSynopsis());
            intent.putExtra(MOVIE_POSTER, movie.getPosterPath());
            intent.putExtra(MOVIE_BACKDROP_PHOTO, movie.getBackdropPhotoPath());

            view.navigateToMovieDetails(intent);
        }
    }
}
