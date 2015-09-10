package de.s3xy.popularmovies.mvp.presenter;

import javax.inject.Inject;

import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractor;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;
import rx.Observer;

/**
 * Created by arueggeberg on 31.07.15.
 */
public class MovieDetailPresenterImpl implements MovieDetailPresenter {

    MovieDetailInteractor interactor;
    private MovieDetailView view;

    @Inject
    public MovieDetailPresenterImpl(MovieDetailInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void bindView(MovieDetailView view) {
        this.view = view;

    }

    @Override
    public void unbindView() {
        view = null;
    }


    @Override
    public void loadMovieDetails(int id) {
        view.showLoading();

        Observer<MovieDetail> obs = new Observer<MovieDetail>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                view.showMovie(movieDetail);
            }

        };
        interactor.loadMovieDetails(obs, id);
    }


}
