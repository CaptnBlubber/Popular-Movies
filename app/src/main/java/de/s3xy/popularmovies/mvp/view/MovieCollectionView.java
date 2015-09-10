package de.s3xy.popularmovies.mvp.view;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;

/**
 * Created by arueggeberg on 31.07.15.
 */
public interface MovieCollectionView {

    void showLoading();
    void hideLoading();
    void showMovies(List<Movie> movies);
    void showError(String errorMessage);
}
