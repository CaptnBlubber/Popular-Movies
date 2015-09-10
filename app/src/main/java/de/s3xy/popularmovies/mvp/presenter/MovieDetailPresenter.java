package de.s3xy.popularmovies.mvp.presenter;

import android.app.Activity;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.view.MovieCollectionView;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by arueggeberg on 31.07.15..
 */
public interface MovieDetailPresenter extends Presenter<MovieDetailView>{
    void loadMovieDetails(int id);
}
