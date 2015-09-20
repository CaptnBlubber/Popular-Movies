package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.MovieDetail;
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
public class MovieDetailFragment extends BaseFragment {

    public static String EXTRA_MOVIE = "details_fragment_movie";

    @Bind(R.id.movie_poster)
    ImageView moviePoster;
    @Bind(R.id.movie_title)
    TextView movieTitle;
    @Bind(R.id.movie_release_date)
    TextView movieReleaseDate;
    @Bind(R.id.movie_runtime)
    TextView movieRuntime;
    @Bind(R.id.movie_rating)
    RatingBar movieRating;
    @Bind(R.id.movie_rating_count)
    TextView movieRatingCount;
    @Bind(R.id.movie_overview)
    TextView movieOverview;
    @Bind(R.id.movie_content)
    LinearLayout movieContent;

    private MovieDetail mMovie;


    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    public static MovieDetailFragment getInstance(MovieDetail movie) {
        MovieDetailFragment instance = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);

        instance.setArguments(args);

        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, v);

        showMovie(mMovie);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mMovie = getArguments().getParcelable(EXTRA_MOVIE);
        } else {
            mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }
    }

    public void showMovie(MovieDetail movie) {
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
                .load(movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(moviePoster);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

}
