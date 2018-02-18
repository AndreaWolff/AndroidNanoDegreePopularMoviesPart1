package com.andrea.popularmovies.dagger.component;

import com.andrea.popularmovies.dagger.module.MainModule;
import com.andrea.popularmovies.dagger.scope.PerActivity;
import com.andrea.popularmovies.features.main.ui.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
