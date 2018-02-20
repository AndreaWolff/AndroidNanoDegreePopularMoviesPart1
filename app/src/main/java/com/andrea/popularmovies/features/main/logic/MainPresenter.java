package com.andrea.popularmovies.features.main.logic;


import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.common.domain.Movie;
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
        retrofit.create(MovieRepository.class)
                .getPopularMoviesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Movie>() {

            @Override public void onCompleted() {
                // do nothing
            }

            @Override public void onError(Throwable e) {
                MainContract.View view = viewWeakReference.get();

                if (view != null) {
                    view.showError(e.getMessage());
                }
            }

            @Override public void onNext(Movie movie) {
                MainContract.View view = viewWeakReference.get();

                if (view != null) {
                    view.showPopularMovies(movie);
                }
            }
        });

    }

    public void onMoviePosterSelected() {
        MainContract.View view = viewWeakReference.get();

        if (view != null) {
            view.navigateToMovieDetails();
        }
    }

    public void disconnectView() {
        viewWeakReference = new WeakReference<>(null);
    }

    public interface MovieRepository {
        @GET("movie/550?api_key=") Observable<Movie> getPopularMoviesList();
    }
}
