package com.andrea.popularmovies.dagger.component;

import android.content.Context;

import com.andrea.popularmovies.dagger.module.AppModule;
import com.andrea.popularmovies.dagger.module.NetModule;
import com.andrea.popularmovies.features.common.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    MovieRepository getMovieRepository();

    Context getContext();
}
