package de.s3xy.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.ui.fragment.BestRatedMoviesCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.FavoriteMoviesCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.MovieCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.MovieDetailFragment;
import de.s3xy.popularmovies.ui.fragment.PopularMoviesCollectionFragment;


public class MovieCollectionFragmentActivity extends BaseActivity implements MovieCollectionFragment.CollectionFragmentInteraction {

    private boolean toolbarSet = false;

    private static final String TAG_FRAGMENT_COLLECTION = "movie_collection_activity_collection_fragment";

    @Bind(R.id.collection_fragment_container)
    FrameLayout collectionFragmentContainer;

    @Nullable
    @Bind(R.id.details_fragment_container)
    FrameLayout detailsFragmentContainer;

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collection_with_fragment);
        ButterKnife.bind(this);

        setupDrawerLayout();

        Fragment f = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_COLLECTION);
        if (f == null) {
            f = PopularMoviesCollectionFragment.getInstance();
        }

        loadCollectionFragment(f);
    }


    protected void setupDrawerLayout() {
        if (drawerLayout != null) {
            NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
            view.setNavigationItemSelectedListener(menuItem -> {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.drawer_best_rated_movies:
                        loadCollectionFragment(BestRatedMoviesCollectionFragment.getInstance());
                        break;
                    case R.id.drawer_popular_movies:
                        loadCollectionFragment(PopularMoviesCollectionFragment.getInstance());
                        break;

                    case R.id.drawer_favorite_movies:
                        loadCollectionFragment(FavoriteMoviesCollectionFragment.getInstance());
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void loadCollectionFragment(Fragment fragment) {
        loadFragment(fragment, R.id.collection_fragment_container, TAG_FRAGMENT_COLLECTION);
    }


    private void loadFragment(Fragment fragment, int containerId, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.commit();
    }


    @Override
    public void setSupportActionBar(Toolbar toolbar, boolean detailFragment) {
        if (!detailFragment) {
            setSupportActionBar(toolbar);
            initToolbar();
            toolbarSet = true;
        }
    }

    @Override
    public void showDetails(Movie movie) {
        if (detailsFragmentContainer != null) {
            String tag = MovieDetailFragment.getTag(movie.getId());
            Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
            if (f == null) {
                f = MovieDetailFragment.getInstance(movie.getId());
            }
            loadFragment(f, R.id.details_fragment_container, tag);
        } else {
            MovieDetailFragmentActivity.startFragmentDetailActivity(this, movie.getId());
        }
    }




}
