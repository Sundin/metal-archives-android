package se.kicksort.metalarchives.model;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 13/03/17.
 */

public class BandSearchResult implements SortedListAdapter.ViewModel {
    @SerializedName("band_name")
    @Expose
    private String name;

    @SerializedName("_id")
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

        return isSameModelAs(o) && isContentTheSameAs(o);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);

        return result;
    }

    @Override
    public <T> boolean isSameModelAs(T item) {
        if (item instanceof BandSearchResult) {
            final BandSearchResult other = (BandSearchResult) item;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(T item) {
        if (item instanceof BandSearchResult) {
            final BandSearchResult other = (BandSearchResult) item;
            if (!name.equals(other.name)) {
                return false;
            }
            if (!country.equals(other.country)) {
                return false;
            }
            return genre != null ? genre.equals(other.genre) : other.genre == null;
        }
        return false;
    }
}
