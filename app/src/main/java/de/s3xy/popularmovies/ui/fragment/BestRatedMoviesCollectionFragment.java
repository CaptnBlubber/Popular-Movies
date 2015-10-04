package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.utils.NetworkUtils;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 21.09.15..
 */
public class BestRatedMoviesCollectionFragment extends MovieCollectionFragment {

    public static BestRatedMoviesCollectionFragment getInstance() {
        return new BestRatedMoviesCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        loadMovies();
        return v;
    }

    private void loadMovies() {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            hideOfflineNotice();
            presenter.loadBestRatedMovies();
        }
        else {
            showOfflineNotice();

            Snackbar.make(listMovies, R.string.wording_general_error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.wording_retry, v -> {
                        loadMovies();
                    })
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }
}
