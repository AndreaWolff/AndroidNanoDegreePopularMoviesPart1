package com.andrea.popularmovies.features.main.logic;

import android.content.Context;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.BaseUnitTest;
import com.andrea.popularmovies.features.common.domain.Movie;
import com.andrea.popularmovies.features.common.repository.MovieRepository;
import com.andrea.popularmovies.features.main.MainContract;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class MainPresenterTest extends BaseUnitTest {

    @Mock private MovieRepository movieRepository;
    @Mock private Context context;
    @Mock private MainContract.View view;

    @Captor private ArgumentCaptor<List<Movie>> movieListArgumentCaptor;
    @Captor private ArgumentCaptor<String> errorStringArgumentCaptor;

    private MainPresenter presenter;

    @Override public void setUp() throws Exception {
        super.setUp();

        presenter = new MainPresenter(movieRepository, context);
    }

    @Test
    public void renderPopularMoviesTitle() {
        // Setup
        when(context.getString(R.string.main_popular_movies_title)).thenReturn("Popular Movies");
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.never());

        // Run
        presenter.connectView(view, null);

        // Verify
        verify(view).renderPopularMoviesTitle("Popular Movies");
    }

    @Test
    public void connectView_loadPopularMovies_returnMovieList() {
        // Setup
        when(context.getString(R.string.main_popular_movies_title)).thenReturn("Popular Movies");
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.just(getMovies()));

        // Run
        presenter.connectView(view, null);

        // Verify
        verify(view).showProgressBar();
        verify(view).hideProgressBar();
        verify(view, times(2)).renderPopularMoviesTitle("Popular Movies");
        verify(view).showMoviesList(movieListArgumentCaptor.capture());

        List<Movie> result = movieListArgumentCaptor.getValue();
        assertThat(result.get(0).getTitle(), is("The Hunt for the Red October"));
    }

    @Test
    public void loadPopularMovies_fromSettings_returnMovieList() {
        // Setup
        when(context.getString(R.string.main_popular_movies_title)).thenReturn("Popular Movies");
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.just(getMovies()));
        presenter.connectView(view, null);
        reset(view);

        // Run
        presenter.loadPopularMovies();

        // Verify
        verify(view).showProgressBar();
        verify(view).hideProgressBar();
        verify(view).renderPopularMoviesTitle("Popular Movies");
        verify(view).showMoviesList(movieListArgumentCaptor.capture());

        List<Movie> result = movieListArgumentCaptor.getValue();
        assertThat(result.get(0).getTitle(), is("The Hunt for the Red October"));
    }

    @Test
    public void connectView_loadPopularMovies_returnError() {
        // Setup
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.error(new Throwable("Unknown Error")));

        // Run
        presenter.connectView(view, null);

        // Verify
        verify(view).showProgressBar();
        verify(view).hideProgressBarOnMovieListError();
        verify(view).showError(errorStringArgumentCaptor.capture());

        String result = errorStringArgumentCaptor.getValue();
        assertThat(result, is("Unknown Error"));
    }

    @Test
    public void loadTopRatedMovies_returnMovieList() {
        // Setup
        when(context.getString(R.string.main_top_rated_movies_title)).thenReturn("Top-Rated Movies");
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.never());
        when(movieRepository.getTopRatedMovieList()).thenReturn(Single.just(getMovies()));
        presenter.connectView(view, null);
        reset(view);

        // Run
        presenter.loadTopRatedMovies();

        // Verify
        verify(view).showProgressBar();
        verify(view).hideProgressBar();
        verify(view).renderTopRatedMoviesTitle("Top-Rated Movies");
        verify(view).showMoviesList(movieListArgumentCaptor.capture());

        List<Movie> result = movieListArgumentCaptor.getValue();
        assertThat(result.get(0).getTitle(), is("The Hunt for the Red October"));
    }

    @Test
    public void loadTopRatedMovies_returnError() {
        // Setup
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.never());
        when(movieRepository.getTopRatedMovieList()).thenReturn(Single.error(new Throwable("Unknown Error")));
        presenter.connectView(view, null);
        reset(view);

        // Run
        presenter.loadTopRatedMovies();

        // Verify
        verify(view).showProgressBar();
        verify(view).hideProgressBarOnMovieListError();
        verify(view).showError(errorStringArgumentCaptor.capture());

        String result = errorStringArgumentCaptor.getValue();
        assertThat(result, is("Unknown Error"));
    }

    @Test
    public void disconnectView() {
        // Setup
        when(movieRepository.getPopularMoviesList()).thenReturn(Single.just(getMovies()));
        presenter.connectView(view, null);
        reset(view);

        // Run
        presenter.disconnectView();

        // Verify
        verifyZeroInteractions(view);
    }

    // region Test Helper
    private List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(getMovie());
        return movies;
    }

    private Movie getMovie() {
        return new Movie("The Hunt for the Red October",
                         "March 2 1990",
                         7.5f,
                         "This is about a boat",
                         "poster image",
                         "background image");
    }
    // endregion
}