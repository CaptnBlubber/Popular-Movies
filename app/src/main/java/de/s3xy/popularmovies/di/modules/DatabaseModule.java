package de.s3xy.popularmovies.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/08/15.
 */
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public Realm provideRealm(Context ctx) {
        return Realm.getInstance(ctx);
    }

}
