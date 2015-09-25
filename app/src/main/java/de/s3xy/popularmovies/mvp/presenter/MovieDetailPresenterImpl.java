package de.s3xy.popularmovies.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.api.models.Trailer;
import de.s3xy.popularmovies.mvp.interactor.MovieDetailInteractor;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;
import rx.Observer;
import timber.log.Timber;

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
    public void loadMovieDetailsFromNetwork(int id) {
        view.showLoading();

        Observer<MovieDetail> obs = new Observer<MovieDetail>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e.getMessage(), e);
                view.showNetworkError(e.getMessage());
            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                view.showMovie(movieDetail);
            }

        };
        interactor.loadMovieDetailsFromNetwork(obs, id);
    }

    @Override
    public void loadMovieDetailsFromDatabase(int id) {
        view.showLoading();

        Observer<MovieDetail> obs = new Observer<MovieDetail>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e.getMessage(), e);
                view.showDatabaseError(e.getMessage());
            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                view.showMovie(movieDetail);
            }

        };
        interactor.loadMovieDetailsFromDatabase(obs, id);
    }

    @Override
    public void toggleFavorite(int movieId) {
        Observer<Boolean> obs = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e.getMessage(), e);

                view.showDatabaseError(e.getMessage());
            }

            @Override
            public void onNext(Boolean favorite) {
                view.markFavorite(favorite, true);
            }
        };

        interactor.toggleFavorite(movieId, obs);
    }

    @Override
    public void getFavoriteState(int movieId) {
        Observer<Boolean> obs = new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showDatabaseError(e.getMessage());
            }

            @Override
            public void onNext(Boolean favorite) {
                view.markFavorite(favorite, false);
            }
        };
        interactor.isMovieFavorite(obs, movieId);
    }

    @Override
    public void playTrailer(Activity activity, Trailer trailer) {
        String youtubeUrl = String.format("http://www.youtube.com/watch?v=%s", trailer.getKey());
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
    }
}
