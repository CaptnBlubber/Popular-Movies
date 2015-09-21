package de.s3xy.popularmovies.di.modules;

import dagger.Module;
import dagger.Provides;
import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.database.DataService;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractorImpl;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractor;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractorImpl;
import de.s3xy.popularmovies.ui.adapter.MovieAdapter;

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
    public MovieCollectionInteractor provideMovieCollectionInteractor(TheMovieDBService theMovieDBService, DataService dataService) {
        return new MovieCollectionInteractorImpl(theMovieDBService, dataService);
    }

    @Provides
    public MovieDetailInteractor provideMovieDetailInteractor(TheMovieDBService theMovieDBService, DataService dataService) {
        return new MovieDetailInteractorImpl(theMovieDBService, dataService);
    }

    @Provides
    public MovieAdapter provideMovieAdapter() {
        return new MovieAdapter();
    }

}
