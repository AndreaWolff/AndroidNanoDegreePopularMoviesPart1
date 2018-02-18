package com.andrea.popularmovies.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.popularmovies.application.MovieApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MovieApplication application;

    public AppModule(@NonNull MovieApplication application) {
        this.application = application;
    }

    @Singleton @Provides Context getContext() {
        return application.getApplicationContext();
    }
}
