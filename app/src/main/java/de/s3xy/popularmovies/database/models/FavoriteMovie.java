package de.s3xy.popularmovies.database.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteMovie extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String posterPath;

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
}