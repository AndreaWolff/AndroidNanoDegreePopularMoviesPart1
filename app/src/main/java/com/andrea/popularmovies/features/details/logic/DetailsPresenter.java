package com.andrea.popularmovies.features.details.logic;


import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.details.DetailsContract;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class DetailsPresenter implements DetailsContract.Presenter {

    private final Context context;

    private WeakReference<DetailsContract.View> viewWeakReference;

    @Inject public DetailsPresenter(@NonNull Context context,
                                    @NonNull DetailsContract.View view) {
        this.context = context;
        viewWeakReference = new WeakReference<>(view);

        init();
    }

    private void init() {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderScreenTitle(context.getString(R.string.details_movie_title));
        }
    }

    public void populateDetails(@NonNull String title,
                                @NonNull String releaseDate,
                                float voteAverage,
                                @NonNull String plotSynopsis,
                                @NonNull String posterPath) {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderMovieTitle(title);
            view.renderReleaseDate(releaseDate);

            voteAverage = voteAverage * 10;
            view.renderVoteAverage((int) voteAverage + "%");

            view.renderPlotSynopsis(plotSynopsis);
            view.renderPosterImage(posterPath);
        }
    }

    public void disconnectView() {
        viewWeakReference = new WeakReference<>(null);
    }

    public void finish() {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.finishActivity();
        }
    }
}
