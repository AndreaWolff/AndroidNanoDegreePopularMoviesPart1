package com.andrea.popularmovies.features.main.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.dagger.component.DaggerMainComponent;
import com.andrea.popularmovies.dagger.module.MainModule;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.details.ui.DetailsActivity;
import com.andrea.popularmovies.features.main.MainContract;
import com.andrea.popularmovies.features.main.logic.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.popularmovies.application.MovieApplication.getDagger;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_PLOT_SYNOPSIS;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_POSTER;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_RELEASE_DATE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_TITLE;
import static com.andrea.popularmovies.features.common.ActivityConstants.MOVIE_VOTE_AVERAGE;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainAdapter.ListItemClickListener {

    @BindView(R.id.recyclerview_main_movie_posters) RecyclerView moviePosterRecyclerView;

    @Inject MainPresenter presenter;
    @Inject Context context;

    private List<Movie> movieList;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                           .appComponent(getDagger())
                           .mainModule(new MainModule(this))
                           .build()
                           .inject(this);

        presenter.loadPopularMovies();

        moviePosterRecyclerView.setLayoutManager(new GridLayoutManager(this, context.getResources().getInteger(R.integer.grid_span_count)));
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

    @Override public void showPopularMovies(@NonNull PopularMovies popularMovies) {
        movieList = new ArrayList<>();
        movieList.addAll(popularMovies.getPopularMovieList());

        List<String> moviePosterPath = new ArrayList<>();

        for (Movie popularMovie : popularMovies.getPopularMovieList()) {
            moviePosterPath.add(popularMovie.getPosterPath());
        }

        MainAdapter adapter = new MainAdapter(this, moviePosterPath);
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

    @Override public void navigateToMovieDetails(int listItem) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_TITLE, movieList.get(listItem).getTitle());
        intent.putExtra(MOVIE_RELEASE_DATE, movieList.get(listItem).getReleaseDate());
        intent.putExtra(MOVIE_VOTE_AVERAGE, movieList.get(listItem).getVoteAverage());
        intent.putExtra(MOVIE_PLOT_SYNOPSIS, movieList.get(listItem).getPlotSynopsis());
        intent.putExtra(MOVIE_POSTER, movieList.get(listItem).getPosterPath());
        startActivity(intent);
    }
    // endregion

    // region Main Adapter
    @Override public void onListItemClick(int listItem) {
        presenter.onMoviePosterSelected(listItem);
    }
    // endregion
}
