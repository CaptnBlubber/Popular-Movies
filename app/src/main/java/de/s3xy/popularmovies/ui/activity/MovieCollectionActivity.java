package de.s3xy.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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


public class MovieCollectionActivity extends BaseActivity implements MovieCollectionView, SwipeRefreshLayout.OnRefreshListener, MovieAdapter.MovieViewHolder.IMovieInteractions {
    @Inject
    MovieCollectionPresenter presenter;

    @Bind(R.id.list_movies)
    RecyclerView mListMovies;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.empty_favorites)
    LinearLayout emptyFavorites;

    private boolean resumed = false;

    @Inject
    MovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collection);
        ButterKnife.bind(this);


        initToolbar();
        setupDrawerLayout();

        mMovieAdapter.registerMovieInteractionListener(this);

        mRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.movie_collection_span_count));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListMovies.setLayoutManager(mLayoutManager);

        mListMovies.addItemDecoration(new SpacingItemDecoration(getResources().getDimension(R.dimen.card_list_spacing)));
        mListMovies.setAdapter(mMovieAdapter);

        presenter.bindView(this);
        presenter.loadPopularMovies();
    }


    protected void setupDrawerLayout() {
        if (drawerLayout != null) {
            NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
            view.setNavigationItemSelectedListener(menuItem -> {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.drawer_best_rated_movies:
                        presenter.loadBestRatedMovies();
                        break;
                    case R.id.drawer_popular_movies:
                        presenter.loadPopularMovies();
                        break;

                    case R.id.drawer_favorite_movies:
                        presenter.loadFavoriteMovies();
                        break;
                }


                drawerLayout.closeDrawers();
                return true;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //If user Comes Back from Detail Activity we want to refresh
        if (resumed) {
            onRefresh();
        }
    }

    @Override
    protected void onPause() {
        resumed = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView();
        presenter.onDestroy();

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * MVP View Implementations
     */

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMovieAdapter.setMovies(movies);
        mListMovies.setVisibility(View.VISIBLE);
        emptyFavorites.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mListMovies, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    onRefresh();
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    public void showEmptyFavorites() {
        mListMovies.setVisibility(View.GONE);
        emptyFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }


    @Override
    public void onMovieClicked(int position) {
        Movie t = mMovieAdapter.getMovie(position);
        presenter.goToDetails(this, t);
    }
}
