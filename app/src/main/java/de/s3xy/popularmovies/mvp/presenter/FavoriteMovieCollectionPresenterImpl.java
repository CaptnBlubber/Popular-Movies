package de.s3xy.popularmovies.mvp.presenter;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public class FavoriteMovieCollectionPresenterImpl extends MovieCollectionPresenterImpl implements FavoriteMovieCollectionPresenter {

    public FavoriteMovieCollectionPresenterImpl(MovieCollectionInteractor interactor) {
        super(interactor);
    }

    @Override
    public void loadFavoriteMovies() {
        mLastAPIMode = MODE_FAVORITE_MOVIES;

        Observer<List<Movie>> obs = new Observer<List<Movie>>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(List<Movie> movies) {
                if (movies.size() > 0) {
                    view.showMovies(movies);
                } else {
                    view.showEmptyFavorites();
                }

            }
        };
        view.showLoading();
        interactor.loadFavoriteMovies(obs);
    }

    @Override
    public void refresh() {
        switch (mLastAPIMode) {
            case MODE_FAVORITE_MOVIES:
                loadFavoriteMovies();
                break;
            default:
                super.refresh();
                break;
        }
    }
}
