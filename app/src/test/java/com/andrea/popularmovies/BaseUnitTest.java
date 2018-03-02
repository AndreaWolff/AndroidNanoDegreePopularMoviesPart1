package com.andrea.popularmovies;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseUnitTest {

    @BeforeClass
    public static void createMainThreadScheduler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
}
