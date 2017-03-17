package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 13/03/17.
 */

public class BandSearchResult {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("country")
    @Expose
    private String country;

    public String getBandName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandSearchResult)) return false;

        BandSearchResult that = (BandSearchResult) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        return country != null ? country.equals(that.country) : that.country == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
