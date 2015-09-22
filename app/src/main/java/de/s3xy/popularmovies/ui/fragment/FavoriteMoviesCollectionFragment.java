package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.presenter.FavoriteMovieCollectionPresenter;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 21.09.15..
 */
public class FavoriteMoviesCollectionFragment extends MovieCollectionFragment {


    @Inject
    FavoriteMovieCollectionPresenter injectedPresenter;

    @Bind(R.id.empty_favorites)
    LinearLayout emptyFavorites;


    @Override
    protected int getLayout() {
        return R.layout.fragment_favorite_movie_collection;
    }


    @Override
    protected void setPresenter() {
        presenter = injectedPresenter;
    }

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    public static FavoriteMoviesCollectionFragment getInstance() {
        return new FavoriteMoviesCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        injectedPresenter.loadFavoriteMovies();
        return v;
    }

    @Override
    public void showEmptyFavorites() {
        emptyFavorites.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }


    @Override
    public void showMovies(List<Movie> movies) {
        emptyFavorites.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        super.showMovies(movies);
    }

}
