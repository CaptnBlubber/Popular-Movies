package de.s3xy.popularmovies.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.ui.fragment.MovieDetailFragment;


/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 09.09.15..
 */
public class MovieDetailFragmentActivity extends BaseActivity {

    public static String EXTRA_MOVIE_ID = "movie_detail_activity_movie_id";
    public static int ILLEGAL_MOVIE_ID = -1;

    private int mMovieId;


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

        if (savedInstanceState == null) {
            mMovieId = getIntent().getExtras().getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
        } else {
            mMovieId = savedInstanceState.getInt(EXTRA_MOVIE_ID, ILLEGAL_MOVIE_ID);
        }

        loadFragment();

    }


    private void loadFragment() {
        String tag = MovieDetailFragment.getTag(mMovieId);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            fragment = MovieDetailFragment.getInstance(mMovieId);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.details_fragment_container, fragment, tag);
        transaction.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_MOVIE_ID, mMovieId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar, boolean detailFragment) {
        setSupportActionBar(toolbar);
        initToolbar(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }
}
