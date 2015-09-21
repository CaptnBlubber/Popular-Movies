package de.s3xy.popularmovies.mvp.interactor;

import de.s3xy.popularmovies.api.TheMovieDBService;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.database.DataService;
import rx.Observable;
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

    DataService dataService;

    public MovieDetailInteractorImpl(TheMovieDBService theMovieDBService, DataService dataService) {
        this.theMovieDBService = theMovieDBService;
        this.dataService = dataService;
    }

    @Override
    public void loadMovieDetails(Observer<? super MovieDetail> observer, int id) {
        Observable
                .combineLatest(
                        theMovieDBService.getMovieDetails(id),
                        theMovieDBService.getMovieReviews(id),
                        theMovieDBService.getMovieTrailers(id),
                        (movieDetail, movieReviewsResponse, movieTrailersResponse) -> {
                            movieDetail.setReviews(movieReviewsResponse.getResults());
                            movieDetail.setTrailers(movieTrailersResponse.getResults());
                            return movieDetail;
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void toggleFavorite(int movieId, Observer<? super Boolean> observer) {

        Observer<Boolean> obs = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                //Stub
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }

            @Override
            public void onNext(Boolean movieExists) {

                if (movieExists) {
                    dataService.deleteMovie(movieId)
                            .map(aBoolean1 -> false)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(observer);
                } else {
                    theMovieDBService
                            .getMovieDetails(movieId)
                            .concatMap(dataService::addFavoriteMovie)
                            .map(movie -> movie != null)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(observer);
                }

            }
        };

        dataService.containsMovie(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(obs);

    }

    @Override
    public void isMovieFavorite(Observer<Boolean> obs, int movieId) {
        dataService
                .containsMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obs);
    }
}
