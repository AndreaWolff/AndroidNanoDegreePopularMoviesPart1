package com.andrea.popularmovies.application;


import android.app.Application;

import com.andrea.popularmovies.dagger.component.AppComponent;
import com.andrea.popularmovies.dagger.component.DaggerAppComponent;
import com.andrea.popularmovies.dagger.module.AppModule;
import com.andrea.popularmovies.dagger.module.NetModule;

public class MovieApplication extends Application {

    private static MovieApplication application;

    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();

        application = this;

        appComponent = createDaggerComponent();
    }

    private AppComponent createDaggerComponent() {
        return DaggerAppComponent.builder()
                                 .appModule(new AppModule(this))
                                 .netModule(new NetModule("http://api.themoviedb.org/3/"))
                                 .build();
    }

    public static AppComponent getDagger() {
        return application.appComponent;
    }
}
