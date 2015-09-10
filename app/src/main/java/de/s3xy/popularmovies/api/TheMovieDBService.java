package de.s3xy.popularmovies.api;

import java.util.List;

import de.s3xy.popularmovies.api.models.MovieCollectionResponse;
import de.s3xy.popularmovies.api.models.MovieDetail;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 06.09.15..
 */
public interface TheMovieDBService {
    @GET("/discover/movie?sort_by=popularity.desc")
    Observable<MovieCollectionResponse> getPopularMovies();

    @GET("/movie/{id}")
    Observable<MovieDetail> getMovieDetails(@Path("id") int id);

    @GET("/discover/movie?sort_by=vote_average.desc")
    Observable<MovieCollectionResponse> getBestRatedMovies();
}
