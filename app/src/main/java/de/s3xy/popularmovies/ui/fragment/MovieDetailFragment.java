package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.solovyev.android.views.llm.DividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.api.models.Review;
import de.s3xy.popularmovies.api.models.Trailer;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import de.s3xy.popularmovies.ui.adapter.ReviewAdapter;
import de.s3xy.popularmovies.ui.adapter.TrailerAdapter;
import de.s3xy.popularmovies.ui.view.HorizontalSpacingItemDecoration;
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
public class MovieDetailFragment extends BaseFragment implements TrailerAdapter.TrailerViewHolder.ITrailerInteractions {

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
    @Bind(R.id.list_trailers)
    RecyclerView listTrailers;
    @Bind(R.id.no_trailers_notice)
    TextView noTrailersNotice;
    @Bind(R.id.list_reviews)
    RecyclerView listReviews;
    @Bind(R.id.no_reviews_notice)
    TextView noReviewsNotice;

    private MovieDetail mMovie;

    @Inject
    TrailerAdapter mTrailerAdapter;

    @Inject
    ReviewAdapter mReviewAdapter;

    @Inject
    MovieDetailPresenter presenter;

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

        showReviews(movie.getReviews());
        showTrailers(movie.getTrailers());

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

    public void showReviews(List<Review> reviews) {
        if (reviews.size() > 0) {
            noReviewsNotice.setVisibility(View.GONE);
            listReviews.setVisibility(View.VISIBLE);

            org.solovyev.android.views.llm.LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(listReviews);
            layoutManager.setOverScrollMode(ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS);
            listReviews.addItemDecoration(new SpacingItemDecoration(getResources().getDimension(R.dimen.review_list_spacing)));
            listReviews.setLayoutManager(layoutManager);
            listReviews.addItemDecoration(new DividerItemDecoration(getContext(), null));
            listReviews.setAdapter(mReviewAdapter);
            mReviewAdapter.setReviews(reviews);
        } else {
            noReviewsNotice.setVisibility(View.VISIBLE);
            listReviews.setVisibility(View.GONE);
        }
    }

    public void showTrailers(List<Trailer> trailers) {
        if (trailers.size() > 0) {
            noTrailersNotice.setVisibility(View.GONE);
            listTrailers.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            listTrailers.addItemDecoration(new HorizontalSpacingItemDecoration(getResources().getDimension(R.dimen.trailer_list_spacing)));
            listTrailers.setLayoutManager(layoutManager);
            listTrailers.setAdapter(mTrailerAdapter);
            mTrailerAdapter.setTrailers(trailers);
        } else {
            noTrailersNotice.setVisibility(View.VISIBLE);
            listTrailers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTrailerAdapter.registerInteractionListener(this);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onTrailerClicked(int position) {
        presenter.playTrailer(getActivity(), mTrailerAdapter.getTrailer(position));
    }
}
