package com.andrea.popularmovies.dagger.module;

import android.support.annotation.NonNull;

import com.andrea.popularmovies.features.common.dao.MovieDao;
import com.andrea.popularmovies.features.common.repository.MovieRepository;
import com.andrea.popularmovies.features.common.repository.MovieRepositoryDefault;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetModule {

    private String baseUrl;

    public NetModule(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton @Provides OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
    }

    @Singleton @Provides Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Singleton @Provides MovieDao movieDao(Retrofit retrofit) {
        return retrofit.create(MovieDao.class);
    }

    @Singleton @Provides MovieRepository movieRepository(MovieRepositoryDefault impl) {
        return impl;
    }
}
