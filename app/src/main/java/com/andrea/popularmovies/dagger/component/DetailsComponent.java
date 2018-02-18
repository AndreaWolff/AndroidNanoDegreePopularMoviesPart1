package com.andrea.popularmovies.dagger.component;

import com.andrea.popularmovies.dagger.module.DetailsModule;
import com.andrea.popularmovies.dagger.scope.PerActivity;
import com.andrea.popularmovies.features.details.ui.DetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = DetailsModule.class)
public interface DetailsComponent {
    void inject(DetailsActivity activity);
}
