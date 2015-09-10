package de.s3xy.popularmovies.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/07/15..
 */
@Module
public class ApplicationModule {

    private final Context appcontext;

    public ApplicationModule(Context context) {
        this.appcontext = context;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.appcontext;
    }


}
