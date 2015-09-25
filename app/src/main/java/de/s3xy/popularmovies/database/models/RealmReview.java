package de.s3xy.popularmovies.database.models;

import de.s3xy.popularmovies.api.models.Review;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmReview extends RealmObject {

    @PrimaryKey
    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmReview() {
    }


    public static Review fromRealmReview(RealmReview realmReview) {
        Review review = new Review();
        review.setId(realmReview.getId());
        review.setAuthor(realmReview.getAuthor());
        review.setContent(realmReview.getContent());
        review.setUrl(realmReview.getUrl());
        return review;
    }

    public RealmReview(Review review) {
        setId(review.getId());
        setUrl(review.getUrl());
        setContent(review.getContent());
        setAuthor(review.getAuthor());
    }

}