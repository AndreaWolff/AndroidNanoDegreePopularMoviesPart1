package com.andrea.popularmovies.features.main.logic;


import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.common.domain.TopRatedMovies;
import com.andrea.popularmovies.features.main.MainContract;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private final Retrofit retrofit;
    private final Context context;

    private WeakReference<MainContract.View> viewWeakReference;

    @Inject public MainPresenter(@NonNull Retrofit retrofit,
                                 @NonNull Context context,
                                 @NonNull MainContract.View view) {
        this.retrofit = retrofit;
        this.context = context;
        viewWeakReference = new WeakReference<>(view);

        init();
    }

    private void init() {
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderPopularMoviesTitle(context.getString(R.string.main_popular_movies_title));
        }
    }

    public void loadPopularMovies() {
        retrofit.create(MovieRepository.class).getPopularMoviesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
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
        });
    }

    public void loadTopRatedMovies() {
        retrofit.create(MovieRepository.class).getTopRatedMoviesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
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
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderPopularMoviesTitle(context.getString(R.string.main_popular_movies_title));
            view.showPopularMovies(popularMovies);
        }
    }

    private void handleTopRatedMoviesResponseSuccessful(TopRatedMovies topRatedMovies) {
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderTopRatedMoviesTitle(context.getString(R.string.main_top_rated_movies_title));
            view.showTopRatedMovies(topRatedMovies);
        }
    }

    private void handleResponseError(Throwable e) {
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.showError(e.getMessage());
        }
    }

    public void onMoviePosterSelected(int listItem) {
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.navigateToMovieDetails(listItem);
        }
    }

    public void disconnectView() {
        viewWeakReference = new WeakReference<>(null);
    }

    public interface MovieRepository {
        @GET("movie/popular?api_key=") Observable<PopularMovies> getPopularMoviesList();

        @GET("movie/top_rated?api_key=") Observable<TopRatedMovies> getTopRatedMoviesList();
    }
}
