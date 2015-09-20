package de.s3xy.popularmovies.mvp.interactor;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public interface MovieCollectionInteractor {

    void loadPopularMovies(Observer<? super List<Movie>> observer);
    void loadBestRatedMovies(Observer<? super List<Movie>> observer);
    void loadFavoriteMovies(Observer<? super List<Movie>> observer);
}
