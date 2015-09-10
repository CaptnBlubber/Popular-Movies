package de.s3xy.popularmovies.mvp.interactor;

import java.util.List;

import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieCollectionResponse;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 31.07.15.
 */
public class MovieDetailInteractorImpl implements MovieDetailInteractor {

    TheMovieDBService theMovieDBService;

    public MovieDetailInteractorImpl(TheMovieDBService theMovieDBService) {
        this.theMovieDBService = theMovieDBService;
    }

    @Override
    public void loadMovieDetails(Observer<? super MovieDetail> observer, int id) {
        theMovieDBService
                .getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
