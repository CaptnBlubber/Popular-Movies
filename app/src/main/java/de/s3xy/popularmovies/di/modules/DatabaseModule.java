package de.s3xy.popularmovies.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.s3xy.popularmovies.database.DataService;
import de.s3xy.popularmovies.database.RealmDataService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(ctx);
        return Realm.getInstance(builder.build());
    }

    @Provides
    @Singleton
    public DataService provideDataService(Context ctx) {
        return new RealmDataService(ctx);
    }



}
