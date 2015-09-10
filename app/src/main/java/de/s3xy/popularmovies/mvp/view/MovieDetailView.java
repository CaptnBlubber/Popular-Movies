package de.s3xy.popularmovies.mvp.view;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 09.09.15..
 */
public interface MovieDetailView {

    void showLoading();
    void hideLoading();
    void showMovie(MovieDetail movie);
    void showError(String errorMessage);

}
