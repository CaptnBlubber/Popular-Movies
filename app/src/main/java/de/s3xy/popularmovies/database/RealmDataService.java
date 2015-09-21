package de.s3xy.popularmovies.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.database.models.FavoriteMovie;
import rx.Observable;

public class RealmDataService implements DataService {

    private final Context context;

    public RealmDataService(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Boolean> containsMovie(int movieId) {
        return RealmObservable
                .results(context, realm -> realm.where(FavoriteMovie.class).equalTo("id", movieId).findAll())
                .map(realmMovies -> realmMovies.size() > 0);
    }

    @Override
    public Observable<Boolean> deleteMovie(int movieId) {
        return RealmObservable
                .delete(context, realm -> realm.where(FavoriteMovie.class).equalTo("id", movieId).findFirst());
    }

    @Override
    public Observable<List<Movie>> favoriteMovies() {
        return RealmObservable.results(context,
                realm -> {
                    // find all movies
                    return realm.where(FavoriteMovie.class).findAll();
                })
                .map(realmMovies -> {
                    // map them to UI objects
                    final List<Movie> issues = new ArrayList<>(realmMovies.size());
                    for (FavoriteMovie realmIssue : realmMovies) {
                        issues.add(movieFromRealm(realmIssue));
                    }
                    return issues;
                });
    }

    @Override
    public Observable<Movie> addFavoriteMovie(MovieDetail movie) {
        return RealmObservable
                .object(context, realm -> {
                    // map internal UI objects to Realm objects
                    final FavoriteMovie realmMovie = new FavoriteMovie();

                    realmMovie.setId(movie.getId());
                    realmMovie.setPosterPath(movie.getPosterPath());
                    realmMovie.setTitle(movie.getTitle());


                    // internal object instances are not created by context
                    // saving them using copyToRealm returning instance associated with context
                    return realm.copyToRealm(realmMovie);

                })
                .map(RealmDataService::movieFromRealm);
    }


    private static Movie movieFromRealm(FavoriteMovie realmMovie) {

        Movie movie = new Movie();
        movie.setTitle(realmMovie.getTitle());
        movie.setId(realmMovie.getId());
        movie.setHttpPosterPath(realmMovie.getPosterPath());

        return movie;
    }

}