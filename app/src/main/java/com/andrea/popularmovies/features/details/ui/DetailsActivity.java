package com.andrea.popularmovies.features.details.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.andrea.popularmovies.dagger.component.DaggerDetailsComponent;
import com.andrea.popularmovies.dagger.module.DetailsModule;
import com.andrea.popularmovies.features.details.DetailsContract;
import com.andrea.popularmovies.features.details.logic.DetailsPresenter;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    @BindView(R.id.textview_details_title) TextView titleTextView;
    @BindView(R.id.textview_details_release_date) TextView releaseDateTextView;
    @BindView(R.id.textview_details_vote_average) TextView voteAverageTextView;
    @BindView(R.id.textview_details_plot_synopsis) TextView plotSynopsisTextView;
    @BindView(R.id.imageview_details_movie_poster) ImageView posterImageView;

    @Inject DetailsPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        DaggerDetailsComponent.builder()
                              .appComponent(MovieApplication.getDagger())
                              .detailsModule(new DetailsModule(this))
                              .build()
                              .inject(this);

        Intent intent = getIntent();
        if (intent == null) {
            // TODO: pass through presenter
            finish();
        }

        String title = intent.getStringExtra(MOVIE_TITLE);
        String releaseDate = intent.getStringExtra(MOVIE_RELEASE_DATE);
        String voteAverage = intent.getStringExtra(MOVIE_VOTE_AVERAGE);
        String plotSynopsis = intent.getStringExtra(MOVIE_PLOT_SYNOPSIS);
        String posterPath = intent.getStringExtra(MOVIE_POSTER);

        presenter.populateDetails(title, releaseDate, voteAverage, plotSynopsis, posterPath);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    // region View methods
    @Override public void renderScreenTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override public void renderMovieTitle(@NonNull String title) {
        titleTextView.setText(title);
    }

    @Override public void renderReleaseDate(@NonNull String releaseDate) {
        releaseDateTextView.setText(releaseDate);
    }

    @Override public void renderVoteAverage(@NonNull String voteAverage) {
        voteAverageTextView.setText(voteAverage);
    }

    @Override public void renderPlotSynopsis(@NonNull String plotSynopsis) {
        plotSynopsisTextView.setText(plotSynopsis);
    }

    @Override public void renderPosterImage(@NonNull String posterPath) {
        Glide.with(this)
             .load(posterPath)
             .into(posterImageView);
    }
    // endregion
}
