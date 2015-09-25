package de.s3xy.popularmovies.mvp.interactor;

import de.s3xy.popularmovies.api.models.MovieDetail;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public interface MovieDetailInteractor {

    void loadMovieDetailsFromNetwork(Observer<? super MovieDetail> observer, int id);
    void loadMovieDetailsFromDatabase(Observer<? super MovieDetail> observer, int id);
    void toggleFavorite(int movieId, Observer<? super Boolean> observer);
    void isMovieFavorite(Observer<Boolean> obs, int movieId);
}
