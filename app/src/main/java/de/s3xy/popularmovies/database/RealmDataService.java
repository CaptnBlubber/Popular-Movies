package de.s3xy.popularmovies.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.api.models.Review;
import de.s3xy.popularmovies.api.models.Trailer;
import de.s3xy.popularmovies.database.models.RealmMovie;
import de.s3xy.popularmovies.database.models.RealmReview;
import de.s3xy.popularmovies.database.models.RealmTrailer;
import io.realm.RealmList;
import rx.Observable;

public class RealmDataService implements DataService {

    private final Context context;

    public RealmDataService(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Boolean> containsMovie(int movieId) {
        return RealmObservable
                .results(context, realm -> realm.where(RealmMovie.class).equalTo("id", movieId).findAll())
                .map(realmMovies -> realmMovies.size() > 0);
    }

    @Override
    public Observable<Boolean> deleteMovie(int movieId) {
        return RealmObservable
                .delete(context, realm -> realm.where(RealmMovie.class).equalTo("id", movieId).findFirst());
    }

    @Override
    public Observable<List<Movie>> favoriteMovies() {
        return RealmObservable.results(context,
                realm -> {
                    // find all movies
                    return realm.where(RealmMovie.class).findAll();
                })
                .map(realmMovies -> {
                    // map them to UI objects
                    final List<Movie> movies = new ArrayList<>(realmMovies.size());
                    for (RealmMovie realmMovie : realmMovies) {
                        movies.add(RealmMovie.movieFromRealm(realmMovie));
                    }
                    return movies;
                });
    }


    @Override
    public Observable<MovieDetail> getFavoriteMovie(int movieId) {
        return RealmObservable
                .object(context, realm -> realm.where(RealmMovie.class).equalTo("id", movieId).findFirst())
                .map(RealmMovie::movieDetailFromRealm);
    }

    @Override
    public Observable<MovieDetail> addFavoriteMovie(MovieDetail movie) {

        final RealmList<RealmTrailer> realmTrailers = new RealmList<>();
        for (RealmTrailer realmTrailer : trailersToRealm(movie.getTrailers())) {
            realmTrailers.add(realmTrailer);
        }


        final RealmList<RealmReview> realmReviews = new RealmList<>();
        for (RealmReview realmReview : reviewsToRealm(movie.getReviews())) {
            realmReviews.add(realmReview);
        }


        return RealmObservable
                .object(context, realm -> {
                    // map internal UI objects to Realm objects
                    final RealmMovie realmMovie = new RealmMovie(movie);

                    RealmList<RealmReview> reviews = new RealmList<>();
                    for (RealmReview review : realmReviews) {
                        reviews.add(realm.copyToRealmOrUpdate(review));
                    }


                    RealmList<RealmTrailer> trailers = new RealmList<>();
                    for (RealmTrailer trailer : realmTrailers) {
                        trailers.add(realm.copyToRealmOrUpdate(trailer));
                    }


                    realmMovie.setTrailers(trailers);
                    realmMovie.setReviews(reviews);

                    // internal object instances are not created by context
                    // saving them using copyToRealm returning instance associated with context
                    return realm.copyToRealmOrUpdate(realmMovie);

                })
                .map(RealmMovie::movieDetailFromRealm);
    }


    private static RealmList<RealmReview> reviewsToRealm(List<Review> reviews) {
        RealmList<RealmReview> realmReviews = new RealmList<>();

        for (Review review : reviews) {
            realmReviews.add(new RealmReview(review));
        }

        return realmReviews;
    }


    private static RealmList<RealmTrailer> trailersToRealm(List<Trailer> trailers) {
        RealmList<RealmTrailer> realmTrailers = new RealmList<>();
        for (Trailer trailer : trailers) {
            realmTrailers.add(new RealmTrailer(trailer));
        }
        return realmTrailers;
    }


}