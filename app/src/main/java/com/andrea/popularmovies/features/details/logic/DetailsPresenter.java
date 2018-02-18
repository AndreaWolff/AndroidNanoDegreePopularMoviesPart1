package com.andrea.popularmovies.features.details.logic;


import android.support.annotation.NonNull;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.andrea.popularmovies.features.details.DetailsContract;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class DetailsPresenter implements DetailsContract.Presenter {

    private WeakReference<DetailsContract.View> viewWeakReference;

    @Inject
    public DetailsPresenter(DetailsContract.View view) {
        viewWeakReference = new WeakReference<>(view);

        init();
    }

    private void init() {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderScreenTitle(MovieApplication.getDagger().getContext().getString(R.string.details_movie_title));
        }
    }

    public void populateDetails(@NonNull String title,
                                @NonNull String releaseDate,
                                @NonNull String voteAverage,
                                @NonNull String plotSynopsis,
                                @NonNull String posterPath) {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderMovieTitle(title);
            view.renderReleaseDate(releaseDate);
            view.renderVoteAverage(voteAverage);
            view.renderPlotSynopsis(plotSynopsis);
            view.renderPosterImage(posterPath);
        }
    }

    public void disconnectView() {
        viewWeakReference = new WeakReference<>(null);
    }
}
