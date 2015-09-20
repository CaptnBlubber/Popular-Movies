package de.s3xy.popularmovies.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;
import de.s3xy.popularmovies.ui.fragment.MovieDetailFragment;
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
public class MovieDetailFragmentActivity extends BaseActivity implements MovieDetailView {

    private static final String TAG_FRAGMENT_DETAIL = "movie_detail_activity_detail_fragment";
    private static final String EXTRA_MOVIE_FAVORITE = "movie_detail_activity_favorite";
    private static final String EXTRA_MOVIE_BACKDROP_PATH = "movie_detail_activity_backdrop_path";
    public static String EXTRA_MOVIE_ID = "movie_detail_activity_movie_id";
    public static int ILLEGAL_MOVIE_ID = -1;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.details_fragment_container)
    FrameLayout detailsFragmentContainer;
    @Bind(R.id.fab_movie_favorite)
    FloatingActionButton fabMovieFavorite;
    @Bind(R.id.loading)
    ProgressBar loading;

    private int mMovieId = ILLEGAL_MOVIE_ID;

    @Inject
    MovieDetailPresenter presenter;

    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    private boolean isFavorite = false;
    private String backdropPath;

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    public static void startFragmentDetailActivity(Activity context, int movieId) {
        Intent startingIntent = new Intent(context, MovieDetailFragmentActivity.class);
        startingIntent.putExtra(EXTRA_MOVIE_ID, movieId);
        context.startActivity(startingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_with_fragment);
        ButterKnife.bind(this);
        presenter.bindView(this);
        initToolbar(true);


        if (savedInstanceState == null) {
            mMovieId = getIntent().getExtras().getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
            presenter.getFavoriteState(mMovieId);
            presenter.loadMovieDetails(mMovieId);
        } else {
            mMovieId = savedInstanceState.getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
            markFavorite(savedInstanceState.getBoolean(EXTRA_MOVIE_FAVORITE, false));
            loadBackdrop(savedInstanceState.getString(EXTRA_MOVIE_BACKDROP_PATH));
            loadFragment(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_DETAIL + mMovieId));
            hideLoading();
        }


    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showMovie(MovieDetail movie) {
        setTitle(movie.getTitle());
        collapsingToolbar.setTitle(movie.getTitle());
        loadBackdrop(movie.getBackdropPath());
        loadFragment(MovieDetailFragment.getInstance(movie));
    }

    public void loadBackdrop(String url) {
        backdropPath = url;
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(backdrop);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.details_fragment_container, fragment, TAG_FRAGMENT_DETAIL + mMovieId);
        transaction.commit();
    }

    @Override
    public void showNetworkError(String errorMessage) {
        Snackbar.make(mainContent, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    presenter.loadMovieDetails(mMovieId);
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    public void showDatabaseError(String errorMessage) {
        Snackbar.make(mainContent, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    presenter.toggleFavorite(mMovieId);
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_MOVIE_ID, mMovieId);
        outState.putBoolean(EXTRA_MOVIE_FAVORITE, isFavorite);
        outState.putString(EXTRA_MOVIE_BACKDROP_PATH, backdropPath);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.fab_movie_favorite)
    public void toggleFavorite() {
        presenter.toggleFavorite(mMovieId);
    }

    @Override
    public void markFavorite(boolean favorite) {
        isFavorite = favorite;
        Glide.with(this).load(favorite ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp).into(fabMovieFavorite);
    }

}
