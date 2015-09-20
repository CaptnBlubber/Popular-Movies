package de.s3xy.popularmovies.di.components;

import javax.inject.Singleton;

import dagger.Component;
import de.s3xy.popularmovies.di.modules.ApplicationModule;
import de.s3xy.popularmovies.di.modules.DatabaseModule;
import de.s3xy.popularmovies.di.modules.NetworkingModule;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/07/15..
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class, DatabaseModule.class})
public interface ApplicationComponent {
    MVPComponent plus();
}
