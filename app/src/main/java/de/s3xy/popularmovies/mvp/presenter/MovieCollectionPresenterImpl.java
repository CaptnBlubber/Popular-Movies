package de.s3xy.popularmovies.mvp.presenter;

import android.app.Activity;

import java.util.List;

import javax.inject.Inject;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import de.s3xy.popularmovies.mvp.view.MovieCollectionView;
import de.s3xy.popularmovies.ui.activity.MovieDetailActivity;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public class MovieCollectionPresenterImpl implements MovieCollectionPresenter {

    MovieCollectionInteractor interactor;
    private MovieCollectionView view;

    private static final int MODE_INVALID = 0;
    private static final int MODE_POPULAR_MOVIES = 1;
    private static final int MODE_BEST_RATED_MOVIES = 2;

    private int mLastAPIMode = MODE_INVALID;

    @Inject
    public MovieCollectionPresenterImpl(MovieCollectionInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void bindView(MovieCollectionView view) {
        this.view = view;

    }

    @Override
    public void unbindView() {
        view = null;
    }


    @Override
    public void goToDetails(Activity context, Movie t) {
        MovieDetailActivity.startDetailActivity(context, t.getId());
    }


    @Override
    public void loadBestRatedMovies() {
        mLastAPIMode = MODE_BEST_RATED_MOVIES;
        view.showLoading();

        Observer<List<Movie>> obs = new Observer<List<Movie>>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(List<Movie> movies) {
                view.showMovies(movies);
            }
        };
        interactor.loadBestRatedMovies(obs);

    }

    @Override
    public void loadPopularMovies() {
        mLastAPIMode = MODE_POPULAR_MOVIES;
        view.showLoading();

        Observer<List<Movie>> obs = new Observer<List<Movie>>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(List<Movie> movies) {
                view.showMovies(movies);
            }
        };
        interactor.loadPopularMovies(obs);

    }

    @Override
    public void refresh() {

        switch (mLastAPIMode) {
            case MODE_POPULAR_MOVIES:
                loadPopularMovies();
                break;
            case MODE_BEST_RATED_MOVIES:
                loadBestRatedMovies();
                break;
            default:
                view.showError("Illegal Refresh State");
        }

    }
}
