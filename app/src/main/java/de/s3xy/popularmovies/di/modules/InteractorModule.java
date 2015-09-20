package de.s3xy.popularmovies.di.modules;

import dagger.Module;
import dagger.Provides;
import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractorImpl;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractor;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractorImpl;
import de.s3xy.popularmovies.ui.adapter.MovieAdapter;
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
public class InteractorModule {

    @Provides
    public MovieCollectionInteractor provideMovieCollectionInteractor(TheMovieDBService theMovieDBService, Realm realmIODatabase) {
        return new MovieCollectionInteractorImpl(theMovieDBService, realmIODatabase);
    }

    @Provides
    public MovieDetailInteractor provideMovieDetailInteractor(TheMovieDBService theMovieDBService, Realm realmIODatabase) {
        return new MovieDetailInteractorImpl(theMovieDBService, realmIODatabase);
    }

    @Provides
    public MovieAdapter provideMovieAdapter() {
        return new MovieAdapter();
    }

}
