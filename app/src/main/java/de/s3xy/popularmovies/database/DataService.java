package de.s3xy.popularmovies.database;

import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;
import rx.Observable;

public interface DataService {
    Observable<List<Movie>> favoriteMovies();
    Observable<Movie> addFavoriteMovie(MovieDetail movie);
    Observable<Boolean> containsMovie(int movieId);
    Observable<Boolean> deleteMovie(int movieID);

}