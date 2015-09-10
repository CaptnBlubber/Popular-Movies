package de.s3xy.popularmovies.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;
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
public class MovieDetailActivity extends BaseActivity implements MovieDetailView {

    public static String EXTRA_MOVIE_ID = "movieId";
    public static int ILLEGAL_MOVIE_ID = -1;

    @Bind(R.id.movie_poster)
    ImageView moviePoster;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.movie_title)
    TextView movieTitle;
    @Bind(R.id.movie_release_date)
    TextView movieReleaseDate;
    @Bind(R.id.movie_overview)
    TextView movieOverview;
    @Bind(R.id.movie_runtime)
    TextView movieRuntime;
    @Bind(R.id.movie_rating)
    RatingBar movieRating;
    @Bind(R.id.movie_rating_count)
    TextView movieRatingCount;
    @Bind(R.id.movie_content)
    LinearLayout movieContent;
    @Bind(R.id.loading)
    ProgressBar loading;

    private int mMovieId = ILLEGAL_MOVIE_ID;

    @Inject
    MovieDetailPresenter presenter;

    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    public static void startDetailActivity(Activity context, int movieId) {
        Intent startingIntent = new Intent(context, MovieDetailActivity.class);
        startingIntent.putExtra(EXTRA_MOVIE_ID, movieId);
        context.startActivity(startingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);


        if (savedInstanceState == null) {
            mMovieId = getIntent().getExtras().getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
        } else {
            mMovieId = savedInstanceState.getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
        }

        initToolbar(true);

        presenter.bindView(this);
        presenter.loadMovieDetails(mMovieId);
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
        movieContent.setVisibility(View.VISIBLE);

        setTitle(movie.getTitle());
        collapsingToolbar.setTitle(movie.getTitle());

        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        movieRuntime.setText(String.format("%d Minutes", movie.getRuntime()));
        movieRating.setRating(movie.getVoteAverage().floatValue());
        movieRatingCount.setText(String.format("%.1f Average | %d Votes", movie.getVoteAverage(), movie.getVoteCount()));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = dateFormat.parse(movie.getReleaseDate());
            dateFormat = new SimpleDateFormat("dd MMM yyyy");
            movieReleaseDate.setText(dateFormat.format(newDate));
        } catch (ParseException e) {
            movieReleaseDate.setText(movie.getReleaseDate());
            Timber.e(e, e.getMessage());
        }


        Glide.with(this)
                .load(movie.getBackdropPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(backdrop);

        Glide.with(this)
                .load(movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(moviePoster);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mainContent, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    presenter.loadMovieDetails(mMovieId);
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_MOVIE_ID, mMovieId);
        super.onSaveInstanceState(outState);
    }
}
