package de.s3xy.popularmovies.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import org.solovyev.android.views.llm.DividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.api.models.Review;
import de.s3xy.popularmovies.api.models.Trailer;
import de.s3xy.popularmovies.mvp.presenter.MovieDetailPresenter;
import de.s3xy.popularmovies.mvp.view.MovieDetailView;
import de.s3xy.popularmovies.ui.activity.BaseActivity;
import de.s3xy.popularmovies.ui.adapter.ReviewAdapter;
import de.s3xy.popularmovies.ui.adapter.TrailerAdapter;
import de.s3xy.popularmovies.ui.view.HorizontalSpacingItemDecoration;
import de.s3xy.popularmovies.ui.view.SpacingItemDecoration;
import de.s3xy.popularmovies.utils.NetworkUtils;
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
public class MovieDetailFragment extends BaseFragment implements MovieDetailView, TrailerAdapter.TrailerViewHolder.ITrailerInteractions, Toolbar.OnMenuItemClickListener {

    private final static String TAG = "details_fragment_movie_";
    private MovieDetail mMovie;

    public static String getTag(int movieId) {
        return TAG + movieId;
    }

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.movie_poster)
    ImageView moviePoster;
    @Bind(R.id.movie_title)
    TextView movieTitle;
    @Bind(R.id.movie_release_date)
    TextView movieReleaseDate;
    @Bind(R.id.movie_runtime)
    TextView movieRuntime;
    @Bind(R.id.no_trailers_notice)
    TextView noTrailersNotice;
    @Bind(R.id.list_trailers)
    RecyclerView listTrailers;
    @Bind(R.id.movie_rating)
    RatingBar movieRating;
    @Bind(R.id.movie_rating_count)
    TextView movieRatingCount;
    @Bind(R.id.movie_overview)
    TextView movieOverview;
    @Bind(R.id.no_reviews_notice)
    TextView noReviewsNotice;
    @Bind(R.id.list_reviews)
    RecyclerView listReviews;
    @Bind(R.id.movie_content)
    LinearLayout movieContent;
    @Bind(R.id.loading)
    ProgressBar loading;
    @Bind(R.id.fab_movie_favorite)
    FloatingActionButton fabMovieFavorite;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout bottomSheetLayout;


    private int mMovieId = ILLEGAL_MOVIE_ID;
    public static String EXTRA_MOVIE_ID = "movie_detail_fragment_movie_id";
    public static int ILLEGAL_MOVIE_ID = -1;


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

    public static MovieDetailFragment getInstance(int movieId) {
        MovieDetailFragment instance = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_MOVIE_ID, movieId);

        instance.setArguments(args);

        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, v);


        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setSupportActionBar(toolbar, true);
        }

        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.details);

        presenter.bindView(this);
        presenter.getFavoriteState(mMovieId);

        if (NetworkUtils.isNetworkAvailable(getContext())) {
            presenter.loadMovieDetailsFromNetwork(mMovieId);
        } else {
            presenter.loadMovieDetailsFromDatabase(mMovieId);
        }

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mMovieId = getArguments().getInt(EXTRA_MOVIE_ID);
        } else {
            mMovieId = savedInstanceState.getInt(EXTRA_MOVIE_ID);
        }
    }


    @Override
    public void showMovie(MovieDetail movie) {

        mMovie = movie;

        collapsingToolbar.setTitle(movie.getTitle());
        loadBackdrop(movie.getFullBackdropPath());

        showReviews(movie.getReviews());
        showTrailers(movie.getTrailers());

        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        movieRuntime.setText(String.format(getResources().getString(R.string.movie_detail_runtime_format), movie.getRuntime()));
        movieRating.setRating(movie.getVoteAverage().floatValue());
        movieRatingCount.setText(String.format(getResources().getString(R.string.movie_detail_rating_format), movie.getVoteAverage(), movie.getVoteCount()));

        try {
            SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = dateFormat.parse(movie.getReleaseDate());
            dateFormat = new SimpleDateFormat("dd MMM yyyy");
            movieReleaseDate.setText(dateFormat.format(newDate));
        } catch (ParseException e) {
            movieReleaseDate.setText(movie.getReleaseDate());
            Timber.e(e, e.getMessage());
        }

        Glide.with(this)
                .load(movie.getFullPosterPath())
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
        outState.putInt(EXTRA_MOVIE_ID, mMovieId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        presenter.unbindView();
    }

    @Override
    public void onTrailerClicked(int position) {
        presenter.playTrailer(getActivity(), mTrailerAdapter.getTrailer(position));
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
    public void showNetworkError(String errorMessage) {
        Snackbar.make(mainContent, R.string.wording_general_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.wording_retry, v -> {
                    presenter.loadMovieDetailsFromNetwork(mMovieId);
                })
                .show();

        Timber.e(errorMessage);
    }

    @Override
    public void showDatabaseError(String errorMessage) {

        Snackbar.make(mainContent, R.string.wording_general_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.wording_retry, v -> {
                    presenter.toggleFavorite(mMovieId);
                })
                .show();

        Timber.e(errorMessage);
    }

    @OnClick(R.id.fab_movie_favorite)
    public void toggleFavorite() {
        presenter.toggleFavorite(mMovieId);
    }

    @Override
    public void markFavorite(boolean favorite, boolean animated) {
        if (animated) {
            animateFavorite(favorite);
        } else {
            setFavoriteDrawable(favorite);
        }

    }

    public void setFavoriteDrawable(boolean favorite) {
        Glide.with(getContext()).load(favorite ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp).into(fabMovieFavorite);
    }


    public void loadBackdrop(String url) {
        Glide.with(getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(backdrop);
    }

    public void animateFavorite(boolean favorite) {

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(fabMovieFavorite, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(fabMovieFavorite, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(fabMovieFavorite, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setFavoriteDrawable(favorite);
            }
        });

        animatorSet.play(rotationAnim);
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);


        animatorSet.start();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.settings_share) {
            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(toolbar.findViewById(R.id.settings_share).getWindowToken(), 0);

            final Intent shareIntent = new Intent(Intent.ACTION_SEND);

            StringBuilder shareTextBuilder = new StringBuilder();

            shareTextBuilder.append(String.format(getResources().getString(R.string.share_text_movie_title), mMovie.getTitle()));

            if (mTrailerAdapter.getItemCount() > 0) {
                shareTextBuilder.append(System.getProperty("line.separator"));
                Trailer trailer = mTrailerAdapter.getTrailer(0);
                shareTextBuilder.append(String.format(getResources().getString(R.string.share_text_trailer), trailer.getYoutubeUrl()));
            }


            shareIntent.putExtra(Intent.EXTRA_TEXT, shareTextBuilder.toString());
            shareIntent.setType("text/plain");


            IntentPickerSheetView intentPickerSheet =
                    new IntentPickerSheetView(
                            getContext(),
                            shareIntent,
                            getResources().getString(R.string.share_dialog_title),
                            activityInfo -> {
                                bottomSheetLayout.dismissSheet();
                                startActivity(activityInfo.getConcreteIntent(shareIntent));
                            });

//            // Filter out built in sharing options such as bluetooth and beam.
//            intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
//                @Override
//                public boolean include(IntentPickerSheetView.ActivityInfo info) {
//                    return !info.componentName.getPackageName().startsWith("com.android");
//                }
//            });
//            // Sort activities in reverse order for no good reason
//            intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
//                @Override
//                public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
//                    return rhs.label.compareTo(lhs.label);
//                }
//            });

            // Add custom mixin example
//            Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
//            IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", PickerActivity.this, MainActivity.class);
//            intentPickerSheet.setMixins(Collections.singletonList(customInfo));

            bottomSheetLayout.showWithSheetView(intentPickerSheet);
        }

        return false;
    }
}
