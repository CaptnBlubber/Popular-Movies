package de.s3xy.popularmovies.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "iso_3166_1",
        "name"
})
public class ProductionCountry implements Parcelable {

    @JsonProperty("iso_3166_1")
    private String iso31661;
    @JsonProperty("name")
    private String name;


    /**
     * @return The iso31661
     */
    @JsonProperty("iso_3166_1")
    public String getIso31661() {
        return iso31661;
    }

    /**
     * @param iso31661 The iso_3166_1
     */
    @JsonProperty("iso_3166_1")
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iso31661);
        dest.writeString(this.name);
    }

    public ProductionCountry() {
    }

    protected ProductionCountry(Parcel in) {
        this.iso31661 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ProductionCountry> CREATOR = new Parcelable.Creator<ProductionCountry>() {
        public ProductionCountry createFromParcel(Parcel source) {
            return new ProductionCountry(source);
        }

        public ProductionCountry[] newArray(int size) {
            return new ProductionCountry[size];
        }
    };
}