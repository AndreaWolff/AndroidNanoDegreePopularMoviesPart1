package com.andrea.popularmovies.dagger.component;

import android.content.Context;

import com.andrea.popularmovies.dagger.module.AppModule;
import com.andrea.popularmovies.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Retrofit getRetrofit();

    Context getContext();
}
