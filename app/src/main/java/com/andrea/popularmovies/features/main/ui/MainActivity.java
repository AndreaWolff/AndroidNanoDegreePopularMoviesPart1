package com.andrea.popularmovies.features.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.dagger.component.DaggerMainComponent;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.andrea.popularmovies.features.main.MainContract;
import com.andrea.popularmovies.features.main.logic.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.andrea.popularmovies.application.MovieApplication.getDagger;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainAdapter.ListItemClickListener {

    @BindView(R.id.recyclerview_main_movie_posters) RecyclerView moviePosterRecyclerView;
    @BindView(R.id.progressbar_loading) ProgressBar loadingProgressBar;

    @Inject MainPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                           .appComponent(getDagger())
                           .build()
                           .inject(this);

        presenter.connectView(this, savedInstanceState);

        moviePosterRecyclerView.setLayoutManager(new GridLayoutManager(this, getApplicationContext().getResources().getInteger(R.integer.grid_span_count)));
        moviePosterRecyclerView.setHasFixedSize(true);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    // region Settings menu
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular_movies:
                presenter.loadPopularMovies();
                return true;
            case R.id.menu_top_rated_movies:
                presenter.loadTopRatedMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // endregion

    // region Main Adapter
    @Override public void onListItemClick(Movie movie) {
        presenter.onMoviePosterSelected(movie);
    }
    // endregion

    // region View Methods
    @Override public void renderPopularMoviesTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override public void renderTopRatedMoviesTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override public void showMoviesList(@NonNull List<Movie> movieList) {
        MainAdapter adapter = new MainAdapter(this, movieList);
        moviePosterRecyclerView.setAdapter(adapter);
    }

    @Override public void showError(@NonNull String title, @NonNull String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    // do nothing
                });
        builder.create();
        builder.show();
    }

    @Override public void showProgressBar() {
        moviePosterRecyclerView.setVisibility(GONE);
        loadingProgressBar.setVisibility(VISIBLE);
    }

    @Override public void hideProgressBar() {
        moviePosterRecyclerView.setVisibility(VISIBLE);
        loadingProgressBar.setVisibility(GONE);
    }

    @Override public void hideProgressBarOnMovieListError() {
        moviePosterRecyclerView.setVisibility(GONE);
        loadingProgressBar.setVisibility(GONE);
    }

    @Override public void navigateToMovieDetails(@NonNull Intent intent) {
        startActivity(intent);
    }
    // endregion
}
