package com.andrea.popularmovies.features.main.logic;


import android.content.Context;

import com.andrea.popularmovies.R;
import com.andrea.popularmovies.features.common.dao.MovieDao;
import com.andrea.popularmovies.features.common.domain.PopularMovies;
import com.andrea.popularmovies.features.main.MainContract;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock private MovieDao movieDao;
    @Mock private Context context;
    @Mock private MainContract.View view;

    private MainPresenter presenter;
    private static DelegatingScheduler delegatingScheduler;

    @BeforeClass
    public static void createDelegatingScheduler() {
        delegatingScheduler = new DelegatingScheduler();

        // taken from https://github.com/ReactiveX/RxJava/issues/3914
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getComputationScheduler() {
                return delegatingScheduler;
            }

            @Override
            public Scheduler getIOScheduler() {
                return delegatingScheduler;
            }

            @Override
            public Scheduler getNewThreadScheduler() {
                return delegatingScheduler;
            }
        });

        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {

            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestScheduler testScheduler = new TestScheduler();
        delegatingScheduler.setInnerScheduler(testScheduler);

        presenter = new MainPresenter(movieDao, context);
    }

    @Test
    public void renderPopularMoviesTitle() {
        // Setup
        when(context.getString(R.string.main_popular_movies_title)).thenReturn("Popular Movies");
        when(movieDao.getPopularMoviesList()).thenReturn(Observable.<PopularMovies>never());

        // Run
        presenter.connectView(view, null);

        // Verify
        verify(view).showProgressBar();
        verify(view).renderPopularMoviesTitle("Popular Movies");
    }

    public static class DelegatingScheduler extends Scheduler {

        private TestScheduler innerScheduler;

        @Override
        public Worker createWorker() {
            return innerScheduler.createWorker();
        }

        @Override
        public long now() {
            return innerScheduler.now();
        }

        void setInnerScheduler(TestScheduler innerScheduler) {
            this.innerScheduler = innerScheduler;
        }
    }
}