package com.andrea.popularmovies.features.details;


import android.support.annotation.NonNull;

public interface DetailsContract {
    interface View {
        void renderScreenTitle(@NonNull String title);

        void renderMovieTitle(@NonNull String title);

        void renderReleaseDate(@NonNull String releaseDate);

        void renderVoteAverage(@NonNull String voteAverage);

        void renderPlotSynopsis(@NonNull String plotSynopsis);

        void renderPosterImage(@NonNull String posterPath);
    }

    interface Presenter {

    }
}
