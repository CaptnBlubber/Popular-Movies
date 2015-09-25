package de.s3xy.popularmovies.database.models;

import de.s3xy.popularmovies.api.models.Trailer;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmTrailer extends RealmObject {

    @PrimaryKey
    private String id;
    private String iso6391;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmTrailer() {
    }


    public static Trailer fromRealmTrailer(RealmTrailer realmTrailer) {
        Trailer trailer = new Trailer();
        trailer.setType(realmTrailer.getType());
        trailer.setSize(realmTrailer.getSize());
        trailer.setId(realmTrailer.getId());
        trailer.setIso6391(realmTrailer.getIso6391());
        trailer.setKey(realmTrailer.getKey());
        trailer.setName(realmTrailer.getName());
        trailer.setSite(realmTrailer.getSite());
        return trailer;
    }

    public RealmTrailer(Trailer trailer) {
        setId(trailer.getId());
        setIso6391(trailer.getIso6391());
        setKey(trailer.getKey());
        setName(trailer.getName());
        setSite(trailer.getSite());
        setSize(trailer.getSize());
        setType(trailer.getType());
    }

    private static RealmTrailer trailerToRealm(Trailer trailer) {
        RealmTrailer realmTrailer = new RealmTrailer();

        realmTrailer.setId(trailer.getId());
        realmTrailer.setIso6391(trailer.getIso6391());
        realmTrailer.setKey(trailer.getKey());
        realmTrailer.setName(trailer.getName());
        realmTrailer.setSite(trailer.getSite());
        realmTrailer.setSize(trailer.getSize());
        realmTrailer.setType(trailer.getType());

        return realmTrailer;
    }

}