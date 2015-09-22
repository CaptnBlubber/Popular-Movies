package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.mvp.presenter.MovieCollectionPresenter;
import de.s3xy.popularmovies.mvp.view.MovieCollectionView;
import de.s3xy.popularmovies.ui.adapter.MovieAdapter;
import de.s3xy.popularmovies.ui.view.SpacingItemDecoration;
import timber.log.Timber;




/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 09.09.15..
 */
public abstract class MovieCollectionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MovieAdapter.MovieViewHolder.IMovieInteractions, MovieCollectionView {

    @Bind(R.id.list_movies)
    RecyclerView listMovies;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean resumed = false;

    protected int getLayout() {
        return R.layout.fragment_movie_collection;
    }

    protected MovieCollectionPresenter presenter;

    @Inject
    MovieCollectionPresenter injectedPresenter;

    protected void setPresenter() {
        presenter = injectedPresenter;
    }

    @Inject
    MovieAdapter mMovieAdapter;

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, v);

        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.movie_collection_span_count));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listMovies.setLayoutManager(mLayoutManager);

        listMovies.addItemDecoration(new SpacingItemDecoration(getResources().getDimension(R.dimen.card_list_spacing)));
        listMovies.setAdapter(mMovieAdapter);

        presenter.bindView(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieAdapter.registerMovieInteractionListener(this);

        if(resumed) {
            presenter.refresh();
        }
    }

    @Override
    public void onPause() {
        resumed = true;
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * MVP View Implementations
     */

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMovieAdapter.setMovies(movies);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(listMovies, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    onRefresh();
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    public void showEmptyFavorites() {
        listMovies.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void onMovieClicked(int position) {
        Movie t = mMovieAdapter.getMovie(position);
        presenter.goToDetails(getActivity(), t);
    }
}
