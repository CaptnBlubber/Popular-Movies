package de.s3xy.popularmovies.database.models;

import java.util.ArrayList;
import java.util.List;

import de.s3xy.popularmovies.api.models.Movie;
import de.s3xy.popularmovies.api.models.MovieDetail;
import de.s3xy.popularmovies.api.models.Review;
import de.s3xy.popularmovies.api.models.Trailer;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovie extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String posterPath;
    private String overview;
    private int runtime;
    private double voteAverage;
    private int voteCount;
    private String releaseDate;

    private RealmList<RealmReview> reviews;
    private RealmList<RealmTrailer> trailers;

    public RealmList<RealmReview> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<RealmReview> reviews) {
        this.reviews = reviews;
    }

    public RealmList<RealmTrailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(RealmList<RealmTrailer> trailers) {
        this.trailers = trailers;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String backdropPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public RealmMovie(MovieDetail movie) {
        setId(movie.getId());
        setTitle(movie.getTitle());
        setRuntime(movie.getRuntime());
        setVoteAverage(movie.getVoteAverage());
        setVoteCount(movie.getVoteCount());
        setOverview(movie.getOverview());
        setReleaseDate(movie.getReleaseDate());
        setPosterPath(movie.getFullPosterPath());
        setBackdropPath(movie.getFullBackdropPath());
    }

    public RealmMovie() {
    }


    public static RealmMovie movieToRealm(MovieDetail movie) {
        RealmMovie realmMovie = new RealmMovie();
        realmMovie.setId(movie.getId());
        realmMovie.setTitle(movie.getTitle());
        realmMovie.setRuntime(movie.getRuntime());
        realmMovie.setVoteAverage(movie.getVoteAverage());
        realmMovie.setVoteCount(movie.getVoteCount());
        realmMovie.setOverview(movie.getOverview());
        realmMovie.setReleaseDate(movie.getReleaseDate());
        realmMovie.setPosterPath(movie.getFullPosterPath());
        realmMovie.setBackdropPath(movie.getFullBackdropPath());

        return realmMovie;
    }

    public static Movie movieFromRealm(RealmMovie realmMovie) {

        Movie movie = new Movie();
        movie.setTitle(realmMovie.getTitle());
        movie.setId(realmMovie.getId());
        movie.setFullPosterPath(realmMovie.getPosterPath());

        return movie;
    }

    public static MovieDetail movieDetailFromRealm(RealmMovie realmMovie) {

        MovieDetail movie = new MovieDetail();
        movie.setTitle(realmMovie.getTitle());
        movie.setId(realmMovie.getId());
        movie.setRuntime(realmMovie.getRuntime());
        movie.setVoteAverage(realmMovie.getVoteAverage());
        movie.setVoteCount(realmMovie.getVoteCount());
        movie.setOverview(realmMovie.getOverview());
        movie.setReleaseDate(realmMovie.getReleaseDate());
        movie.setFullPosterPath(realmMovie.getPosterPath());
        movie.setFullBackdropPath(realmMovie.getBackdropPath());

        List<Review> reviews = new ArrayList<>();
        for (RealmReview review : realmMovie.getReviews()) {
            reviews.add(RealmReview.fromRealmReview(review));
        }
        movie.setReviews(reviews);

        List<Trailer> trailers = new ArrayList<>();
        for (RealmTrailer trailer : realmMovie.getTrailers()) {
            trailers.add(RealmTrailer.fromRealmTrailer(trailer));
        }

        movie.setTrailers(trailers);

        return movie;
    }
}