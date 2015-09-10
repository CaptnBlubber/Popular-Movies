package de.s3xy.popularmovies.mvp.interactor;

import java.util.List;
import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieCollectionResponse;
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
public class MovieCollectionInteractorImpl implements MovieCollectionInteractor {

    TheMovieDBService theMovieDBService;

    public MovieCollectionInteractorImpl(TheMovieDBService theMovieDBService) {
        this.theMovieDBService = theMovieDBService;
    }

    @Override
    public void loadPopularMovies(Observer<? super List<Movie>> observer) {
        theMovieDBService
                .getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MovieCollectionResponse::getResults)
                .subscribe(observer);
    }

    @Override
    public void loadBestRatedMovies(Observer<? super List<Movie>> observer) {
        theMovieDBService
                .getBestRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MovieCollectionResponse::getResults)
                .subscribe(observer);
    }

}
