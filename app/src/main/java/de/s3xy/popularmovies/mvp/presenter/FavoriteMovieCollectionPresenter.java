package de.s3xy.popularmovies.mvp.presenter;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by arueggeberg on 31.07.15..
 */
public interface FavoriteMovieCollectionPresenter extends MovieCollectionPresenter{
    void loadFavoriteMovies();
}
