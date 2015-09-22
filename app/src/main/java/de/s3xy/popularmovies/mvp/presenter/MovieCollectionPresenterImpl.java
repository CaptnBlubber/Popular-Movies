package de.s3xy.popularmovies.mvp.presenter;

import java.util.List;

import javax.inject.Inject;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.interactor.MovieCollectionInteractor;
import de.s3xy.popularmovies.mvp.view.MovieCollectionView;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public class MovieCollectionPresenterImpl implements MovieCollectionPresenter {

    protected MovieCollectionInteractor interactor;
    protected MovieCollectionView view;

    protected static final int MODE_INVALID = 0;
    protected static final int MODE_POPULAR_MOVIES = 1;
    protected static final int MODE_BEST_RATED_MOVIES = 2;
    protected static final int MODE_FAVORITE_MOVIES = 3;

    protected int mLastAPIMode = MODE_INVALID;

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



    private Observer<List<Movie>> getDefaultMovieObserver() {

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
                view.showMovies(movies);
            }
        };

        return obs;
    }


    @Override
    public void loadBestRatedMovies() {
        mLastAPIMode = MODE_BEST_RATED_MOVIES;
        view.showLoading();
        interactor.loadBestRatedMovies(getDefaultMovieObserver());

    }

    @Override
    public void loadPopularMovies() {
        mLastAPIMode = MODE_POPULAR_MOVIES;
        view.showLoading();
        interactor.loadPopularMovies(getDefaultMovieObserver());
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
