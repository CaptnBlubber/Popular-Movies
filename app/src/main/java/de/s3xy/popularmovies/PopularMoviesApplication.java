package de.s3xy.popularmovies;

import android.app.Application;

import de.s3xy.popularmovies.di.components.ApplicationComponent;
import de.s3xy.popularmovies.di.components.DaggerApplicationComponent;
import de.s3xy.popularmovies.di.modules.ApplicationModule;
import timber.log.Timber;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/07/15..
 */
public class PopularMoviesApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        Timber.plant(new Timber.DebugTree());
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public void setTestMode(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
