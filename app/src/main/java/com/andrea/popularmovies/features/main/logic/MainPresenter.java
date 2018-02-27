package com.andrea.popularmovies.features.main.logic;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

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

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_BACKDROP_PHOTO;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

public class MainPresenter implements MainContract.Presenter {

    private static final String MOVIE_LIST = "MAPML";

    private final CompositeSubscription subscriptions = new CompositeSubscription();
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
        subscriptions.clear();
        view = null;
    }

    public void loadPopularMovies() {
        if (view != null) {
            view.showProgressBar();
        }

        subscriptions.add(movieRepository.getPopularMoviesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PopularMovies>() {
            @Override public void onCompleted() {
                // do nothing
            }

            @Override public void onError(Throwable e) {
                handleResponseError(e);
            }

            @Override public void onNext(PopularMovies popularMovies) {
                handlePopularMoviesResponseSuccessful(popularMovies);
            }
        }));
    }

    public void loadTopRatedMovies() {
        if (view != null) {
            view.showProgressBar();
        }

        movieRepository.getTopRatedMovieList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopRatedMovies>() {
            @Override public void onCompleted() {
                // do nothing
            }

            @Override public void onError(Throwable e) {
                handleResponseError(e);
            }

            @Override public void onNext(TopRatedMovies topRatedMovies) {
                handleTopRatedMoviesResponseSuccessful(topRatedMovies);
            }
        });
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

    private void handleResponseError(Throwable e) {
        if (view != null) {
            view.showError(new AlertDialog.Builder(context).setMessage(e.getMessage()).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    // do nothing
                }
            }));
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
