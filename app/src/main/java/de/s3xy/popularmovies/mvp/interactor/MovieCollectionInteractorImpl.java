package de.s3xy.popularmovies.mvp.interactor;

import java.util.ArrayList;
import java.util.List;

import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieCollectionResponse;
import de.s3xy.popularmovies.database.models.FavoriteMovie;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
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

    Realm database;
    TheMovieDBService theMovieDBService;

    public MovieCollectionInteractorImpl(TheMovieDBService theMovieDBService, Realm realmIODatabase) {
        this.theMovieDBService = theMovieDBService;
        this.database = realmIODatabase;
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

    @Override
    public void loadFavoriteMovies(Observer<? super List<Movie>> observer) {
        Observable
                .create(new Observable.OnSubscribe<RealmResults<FavoriteMovie>>() {
                    @Override
                    public void call(Subscriber<? super RealmResults<FavoriteMovie>> subscriber) {
                        subscriber.onNext(database.where(FavoriteMovie.class).findAll());
                        subscriber.onCompleted();
                    }
                })

                .map(favoriteMovies -> {
                    ArrayList<Movie> movies = new ArrayList<>();
                    for (FavoriteMovie movie : favoriteMovies) {
                        Movie m = new Movie();
                        m.setId(movie.getId());
                        m.setTitle(movie.getTitle());
                        m.setHttpPosterPath(movie.getPosterPath());
                        movies.add(m);
                    }
                    return movies;
                })
                .subscribe(observer);
    }
}
