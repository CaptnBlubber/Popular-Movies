package de.s3xy.popularmovies.mvp.interactor;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public interface MovieDetailInteractor {

    void loadMovieDetails(Observer<? super MovieDetail> observer, int id);

}
