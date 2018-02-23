package com.andrea.popularmovies.features.details.logic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.details.DetailsContract;

import java.lang.ref.WeakReference;
import java.util.Locale;

import javax.inject.Inject;

import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_BACKDROP_PHOTO;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

@SuppressWarnings("ConstantConditions")
public class DetailsPresenter implements DetailsContract.Presenter {

    private final Context context;

    private WeakReference<DetailsContract.View> viewWeakReference;
    private String title;
    private String releaseDate;
    private float voteAverage;
    private String plotSynopsis;
    private String posterPath;
    private String backdropPhotoPath;

    @Inject DetailsPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@NonNull DetailsContract.View view, @Nullable Intent intent) {
        viewWeakReference = new WeakReference<>(view);

        if (intent == null) {
            view.finishActivity();
        }

        Bundle extras = intent.getExtras();
        if (extras != null) {
            title = extras.getString(MOVIE_TITLE);
            releaseDate = extras.getString(MOVIE_RELEASE_DATE);
            voteAverage = extras.getFloat(MOVIE_VOTE_AVERAGE);
            plotSynopsis = extras.getString(MOVIE_PLOT_SYNOPSIS);
            posterPath = extras.getString(MOVIE_POSTER);
            backdropPhotoPath = extras.getString(MOVIE_BACKDROP_PHOTO);
        }

        init();
    }

    private void init() {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderScreenTitle(context.getString(R.string.details_movie_title));
        }

        populateDetails(title, releaseDate, voteAverage, plotSynopsis, posterPath, backdropPhotoPath);
    }

    public void populateDetails(@NonNull String title,
                                @NonNull String releaseDate,
                                float voteAverage,
                                @NonNull String plotSynopsis,
                                @NonNull String posterPath,
                                @NonNull String backdropPhotoPath) {
        DetailsContract.View view = viewWeakReference.get();

        if (view != null) {
            view.renderMovieTitle(title);
            view.renderReleaseDate(releaseDate);

            float starRating = Float.parseFloat(String.format(Locale.ENGLISH, "%.2f", voteAverage));
            float voteAvg = voteAverage * 10;
            view.renderVoteAverage((int) voteAvg + "%", starRating/2);

            view.renderPlotSynopsis(plotSynopsis);
            view.renderPosterImage(posterPath);
            view.renderBackdropPhoto(backdropPhotoPath);
        }
    }

    public void disconnectView() {
        viewWeakReference = new WeakReference<>(null);
    }
}
