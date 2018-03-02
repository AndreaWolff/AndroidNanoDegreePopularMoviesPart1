package com.andrea.popularmovies.features.common.domain;

import com.andrea.popularmovies.BaseUnitTest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MovieTest extends BaseUnitTest {

    @Test
    public void movie_getters() {
        // Setup

        // Run
        Movie movie = new Movie("The Hunt for the Red October",
                                "March 2 1990",
                                7.5f,
                                "This is about a boat",
                                "poster image",
                                "background image");


        // Verify
        assertThat(movie.getTitle(), is("The Hunt for the Red October"));
        assertThat(movie.getReleaseDate(), is("March 2 1990"));
        assertThat(movie.getVoteAverage(), is(7.5f));
        assertThat(movie.getPlotSynopsis(), is("This is about a boat"));
        assertThat(movie.getPosterPath(), is("http://image.tmdb.org/t/p/w185poster image"));
        assertThat(movie.getBackdropPhotoPath(), is("http://image.tmdb.org/t/p/w185background image"));
    }
}