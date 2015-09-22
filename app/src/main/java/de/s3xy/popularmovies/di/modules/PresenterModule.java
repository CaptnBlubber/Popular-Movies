package de.s3xy.popularmovies.di.modules;

import dagger.Module;
import dagger.Provides;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractor;
import de.s3xy.popularmovies.mvp.presenter.FavoriteMovieCollectionPresenter;
import de.s3xy.popularmovies.mvp.presenter.FavoriteMovieCollectionPresenterImpl;
import de.s3xy.popularmovies.mvp.presenter.MovieCollectionPresenter;
import de.s3xy.popularmovies.mvp.presenter.MovieCollectionPresenterImpl;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenterImpl;

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
public class PresenterModule {

    @Provides
    MovieCollectionPresenter provideMovieCollectionPresenter(MovieCollectionInteractor interactor) {
        return new MovieCollectionPresenterImpl(interactor);
    }

    @Provides
    FavoriteMovieCollectionPresenter provideFavoriteMovieCollectionPresenter(MovieCollectionInteractor interactor) {
        return new FavoriteMovieCollectionPresenterImpl(interactor);
    }

    @Provides
    MovieDetailPresenter provideMovieDetailPresenter(MovieDetailInteractor interactor) {
        return new MovieDetailPresenterImpl(interactor);
    }

}
