package de.s3xy.popularmovies.mvp.presenter;

import android.app.Activity;

import de.s3xy.popularmovies.api.models.Trailer;
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
    void loadMovieDetailsFromNetwork(int id);
    void loadMovieDetailsFromDatabase(int id);
    void toggleFavorite(int id);
    void getFavoriteState(int id);
    void playTrailer(Activity activity, Trailer trailer);
}
