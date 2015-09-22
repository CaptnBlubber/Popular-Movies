package de.s3xy.popularmovies.mvp.presenter;

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

    void loadPopularMovies();
    void loadBestRatedMovies();

    void refresh();
}
