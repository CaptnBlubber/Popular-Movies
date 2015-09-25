package de.s3xy.popularmovies.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "adult",
        "backdrop_path",
        "budget",
        "genres",
        "homepage",
        "id",
        "imdb_id",
        "original_language",
        "original_title",
        "overview",
        "popularity",
        "poster_path",
        "production_companies",
        "production_countries",
        "release_date",
        "revenue",
        "runtime",
        "spoken_languages",
        "status",
        "tagline",
        "title",
        "video",
        "vote_average",
        "vote_count"
})

@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieDetail implements Parcelable {

    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("budget")
    private Integer budget;
    @JsonProperty("genres")
    private List<Genre> genres = new ArrayList<Genre>();
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("revenue")
    private Integer revenue;
    @JsonProperty("runtime")
    private Integer runtime;
    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();
    @JsonProperty("status")
    private String status;
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("title")
    private String title;
    @JsonProperty("video")
    private Boolean video;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    private List<Review> reviews;
    private List<Trailer> trailers;


    private String fullBackdropPath;
    private String fullPosterPath;

    /**
     * @return The adult
     */
    @JsonProperty("adult")
    public Boolean getAdult() {
        return adult;
    }

    /**
     * @param adult The adult
     */
    @JsonProperty("adult")
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     * @return The backdropPath
     */
    @JsonProperty("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @param backdropPath The backdrop_path
     */
    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public String getFullBackdropPath() {
        if(fullBackdropPath != null) {
            return fullBackdropPath;
        }
        else {
            return "https://image.tmdb.org/t/p/original" + getBackdropPath();
        }
    }

    public void setFullBackdropPath(String fullBackdropPath) {
        this.fullBackdropPath = fullBackdropPath;
    }

    public String getFullPosterPath() {
        if(fullPosterPath != null) {
            return fullPosterPath;
        }
        else {
            return "https://image.tmdb.org/t/p/original" + getPosterPath();
        }

    }

    public void setFullPosterPath(String fullPosterPath) {
        this.fullPosterPath = fullPosterPath;
    }


    /**
     * @return The budget
     */
    @JsonProperty("budget")
    public Integer getBudget() {
        return budget;
    }

    /**
     * @param budget The budget
     */
    @JsonProperty("budget")
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    /**
     * @return The genres
     */
    @JsonProperty("genres")
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    @JsonProperty("genres")
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * @return The homepage
     */
    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    /**
     * @param homepage The homepage
     */
    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The imdbId
     */
    @JsonProperty("imdb_id")
    public String getImdbId() {
        return imdbId;
    }

    /**
     * @param imdbId The imdb_id
     */
    @JsonProperty("imdb_id")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /**
     * @return The originalLanguage
     */
    @JsonProperty("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * @param originalLanguage The original_language
     */
    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * @return The originalTitle
     */
    @JsonProperty("original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle The original_title
     */
    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * @return The overview
     */
    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */
    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The popularity
     */
    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    /**
     * @param popularity The popularity
     */
    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     * @return The posterPath
     */
    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath The poster_path
     */
    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return The productionCompanies
     */
    @JsonProperty("production_companies")
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     * @param productionCompanies The production_companies
     */
    @JsonProperty("production_companies")
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    /**
     * @return The productionCountries
     */
    @JsonProperty("production_countries")
    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    /**
     * @param productionCountries The production_countries
     */
    @JsonProperty("production_countries")
    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    /**
     * @return The releaseDate
     */
    @JsonProperty("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The release_date
     */
    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return The revenue
     */
    @JsonProperty("revenue")
    public Integer getRevenue() {
        return revenue;
    }

    /**
     * @param revenue The revenue
     */
    @JsonProperty("revenue")
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    /**
     * @return The runtime
     */
    @JsonProperty("runtime")
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * @param runtime The runtime
     */
    @JsonProperty("runtime")
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     * @return The spokenLanguages
     */
    @JsonProperty("spoken_languages")
    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    /**
     * @param spokenLanguages The spoken_languages
     */
    @JsonProperty("spoken_languages")
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    /**
     * @return The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The tagline
     */
    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    /**
     * @param tagline The tagline
     */
    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * @return The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The video
     */
    @JsonProperty("video")
    public Boolean getVideo() {
        return video;
    }

    /**
     * @param video The video
     */
    @JsonProperty("video")
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     * @return The voteAverage
     */
    @JsonProperty("vote_average")
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage The vote_average
     */
    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * @return The voteCount
     */
    @JsonProperty("vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * @param voteCount The vote_count
     */
    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }


    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }


    public MovieDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.adult);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.budget);
        dest.writeList(this.genres);
        dest.writeString(this.homepage);
        dest.writeValue(this.id);
        dest.writeString(this.imdbId);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeList(this.productionCompanies);
        dest.writeList(this.productionCountries);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.revenue);
        dest.writeValue(this.runtime);
        dest.writeList(this.spokenLanguages);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
        dest.writeList(this.reviews);
        dest.writeList(this.trailers);
    }

    protected MovieDetail(Parcel in) {
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backdropPath = in.readString();
        this.budget = (Integer) in.readValue(Integer.class.getClassLoader());
        this.genres = new ArrayList<Genre>();
        in.readList(this.genres, List.class.getClassLoader());
        this.homepage = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imdbId = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.posterPath = in.readString();
        this.productionCompanies = new ArrayList<ProductionCompany>();
        in.readList(this.productionCompanies, List.class.getClassLoader());
        this.productionCountries = new ArrayList<ProductionCountry>();
        in.readList(this.productionCountries, List.class.getClassLoader());
        this.releaseDate = in.readString();
        this.revenue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runtime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spokenLanguages = new ArrayList<SpokenLanguage>();
        in.readList(this.spokenLanguages, List.class.getClassLoader());
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reviews = new ArrayList<Review>();
        in.readList(this.reviews, List.class.getClassLoader());
        this.trailers = new ArrayList<Trailer>();
        in.readList(this.trailers, List.class.getClassLoader());
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        public MovieDetail createFromParcel(Parcel source) {
            return new MovieDetail(source);
        }

        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };
}