package com.andrea.popularmovies.features.main.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.application.MovieApplication;
import com.andrea.popularmovies.dagger.component.DaggerMainComponent;
import com.andrea.popularmovies.dagger.module.MainModule;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.andrea.popularmovies.features.details.ui.DetailsActivity;
import com.andrea.popularmovies.features.main.MainContract;
import com.andrea.popularmovies.features.main.logic.MainPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 10;

    @BindView(R.id.recyclerview_main_movie_posters) RecyclerView moviePosterRecyclerView;

    private Movie movie;

    @Inject MainPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                           .appComponent(MovieApplication.getDagger())
                           .mainModule(new MainModule(this))
                           .build()
                           .inject(this);

        presenter.loadPopularMovies();

        moviePosterRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviePosterRecyclerView.setHasFixedSize(true);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    // region View methods
    @Override public void renderPopularMoviesTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override public void showPopularMovies(@NonNull Movie movie) {
        this.movie = movie;

        MainAdapter adapter = new MainAdapter(this, movie, NUM_LIST_ITEMS);
        moviePosterRecyclerView.setAdapter(adapter);
    }

    @Override public void showError(@NonNull String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                });
        builder.show();
    }

    @Override public void navigateToMovieDetails() {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_TITLE, movie.getTitle());
        intent.putExtra(MOVIE_RELEASE_DATE, movie.getReleaseDate());
        intent.putExtra(MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        intent.putExtra(MOVIE_PLOT_SYNOPSIS, movie.getPlotSynopsis());
        intent.putExtra(MOVIE_POSTER, movie.getPosterPath());
        startActivity(intent);
    }
    // endregion

    // region Main Adapter
    @Override public void onListItemClick(int listItem) {
        presenter.onMoviePosterSelected();
    }
    // endregion
}
