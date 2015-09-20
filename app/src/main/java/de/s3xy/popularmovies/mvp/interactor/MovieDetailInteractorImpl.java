package de.s3xy.popularmovies.mvp.interactor;

import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.database.models.FavoriteMovie;
import io.realm.Realm;
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

    Realm database;

    public MovieDetailInteractorImpl(TheMovieDBService theMovieDBService, Realm realmIODatabase) {
        this.theMovieDBService = theMovieDBService;
        this.database = realmIODatabase;
    }

    @Override
    public void loadMovieDetails(Observer<? super MovieDetail> observer, int id) {
        theMovieDBService
                .getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void toggleFavorite(int movieId, Observer<? super Boolean> observer) {

        if (isMovieFavorite(movieId)) {
            observer.onNext(false);
            try {
                deleteMovieFromDatabase(movieId);
            } catch (Exception e) {
                database.cancelTransaction();
                observer.onError(e);
            }
            observer.onCompleted();
        } else {
            theMovieDBService
                    .getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(this::addMovieToDatabase)
                    .subscribe(observer);
        }
    }

    public boolean addMovieToDatabase(MovieDetail movieDetail) {
        database.beginTransaction();
        FavoriteMovie movie = database.createObject(FavoriteMovie.class);
        movie.setId(movieDetail.getId());
        movie.setTitle(movieDetail.getTitle());
        movie.setPosterPath(movieDetail.getPosterPath());
        database.commitTransaction();
        return true;
    }


    @Override
    public boolean isMovieFavorite(int movieId) {
        return database.where(FavoriteMovie.class).equalTo("id", movieId).findFirst() != null;
    }

    public void deleteMovieFromDatabase(int movieId) throws Exception {
        database.beginTransaction();
        database.where(FavoriteMovie.class).equalTo("id", movieId).findFirst().removeFromRealm();
        database.commitTransaction();
    }
}
