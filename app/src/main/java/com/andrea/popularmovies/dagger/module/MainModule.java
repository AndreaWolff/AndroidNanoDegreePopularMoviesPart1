package com.andrea.popularmovies.dagger.module;

import android.support.annotation.NonNull;

import com.andrea.popularmovies.dagger.scope.PerActivity;
import com.andrea.popularmovies.features.main.MainContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainContract.View view;

    public MainModule(@NonNull MainContract.View view) {
        this.view = view;
    }

    @PerActivity @Provides MainContract.View getMainView() {
        return view;
    }
}
