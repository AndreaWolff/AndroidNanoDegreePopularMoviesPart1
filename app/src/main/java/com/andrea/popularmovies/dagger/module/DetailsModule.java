package com.andrea.popularmovies.dagger.module;

import android.support.annotation.NonNull;

import com.andrea.popularmovies.dagger.scope.PerActivity;
import com.andrea.popularmovies.features.details.DetailsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsModule {

    private final DetailsContract.View view;

    public DetailsModule(@NonNull DetailsContract.View view) {
        this.view = view;
    }

    @PerActivity @Provides DetailsContract.View getDetailsView() {
        return view;
    }
}
