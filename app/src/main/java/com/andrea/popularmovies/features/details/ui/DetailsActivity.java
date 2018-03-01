package com.andrea.popularmovies.features.details.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.andrea.popularmovies.dagger.component.DaggerDetailsComponent;
import com.andrea.popularmovies.features.details.DetailsContract;
import com.andrea.popularmovies.features.details.logic.DetailsPresenter;
import com.andrea.popularmovies.util.GlideUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    @BindView(R.id.textview_details_title) TextView titleTextView;
    @BindView(R.id.textview_details_release_date) TextView releaseDateTextView;
    @BindView(R.id.ratingbar_voting_average_rating) RatingBar voteAverageRatingBar;
    @BindView(R.id.textview_details_vote_average) TextView voteAverageTextView;
    @BindView(R.id.textview_details_plot_synopsis) TextView plotSynopsisTextView;
    @BindView(R.id.imageview_details_movie_poster) ImageView posterImageView;
    @BindView(R.id.imageview_details_backdrop_photo) ImageView backdropPhotoImageView;

    @Inject DetailsPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        DaggerDetailsComponent.builder()
                              .appComponent(MovieApplication.getDagger())
                              .build()
                              .inject(this);

        presenter.connectView(this, getIntent());
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override public void renderVoteAverage(@NonNull String voteAverage, float rating) {
        voteAverageRatingBar.setRating(rating);
        voteAverageTextView.setText(voteAverage);
    }

    @Override public void renderPlotSynopsis(@NonNull String plotSynopsis) {
        plotSynopsisTextView.setText(plotSynopsis);
    }

    @Override public void renderPosterImage(@NonNull String posterPath) {
        GlideUtil.displayImage(posterPath, posterImageView);
    }

    @Override public void renderBackdropPhoto(@NonNull String backdropPhotoPath) {
        GlideUtil.displayImage(backdropPhotoPath, backdropPhotoImageView);
    }

    @Override public void finishActivity() {
        finish();
    }
    // endregion
}
