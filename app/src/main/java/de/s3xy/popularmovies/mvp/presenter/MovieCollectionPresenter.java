package de.s3xy.popularmovies.mvp.presenter;

import android.app.Activity;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.view.MovieCollectionView;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by arueggeberg on 31.07.15..
 */
public interface MovieCollectionPresenter extends Presenter<MovieCollectionView>{

    void goToDetails(Activity context, Movie t);

    void loadPopularMovies();
    void loadFavoriteMovies();
    void loadBestRatedMovies();

    void refresh();
}
