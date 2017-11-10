package se.kicksort.metalarchives.model;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 13/03/17.
 */

public class BandSearchResult implements SortedListAdapter.ViewModel {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("_score")
    @Expose
    private Double searchScore;

    @SerializedName("_source")
    @Expose
    private SearchResultsInnerData source;

    public String getId() {
        return id;
    }

    public Double getSearchScore() {
        return searchScore;
    }

    public String getBandName() {
        return source.getBandName();
    }

    public String getGenre() {
        return source.getGenre();
    }

    public String getCountry() {
        return source.getCountry();
    }

    public String getStatus() {
        return source.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandSearchResult)) return false;

        return isSameModelAs(o) && isContentTheSameAs(o);
    }

    @Override
    public int hashCode() {
        int result = source.getBandName() != null ? source.getBandName().hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (source.getGenre() != null ? source.getGenre().hashCode() : 0);
        result = 31 * result + (source.getCountry() != null ? source.getCountry().hashCode() : 0);

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
            if (!source.getBandName().equals(other.getBandName())) {
                return false;
            }
            if (!source.getCountry().equals(other.getCountry())) {
                return false;
            }
            return source.getGenre() != null ? source.getGenre().equals(other.getGenre()) : other.getGenre() == null;
        }
        return false;
    }
}

class SearchResultsInnerData {
    @SerializedName("band_name")
    @Expose
    private String name = "";

    @SerializedName("genre")
    @Expose
    private String genre = "";

    @SerializedName("country")
    @Expose
    private String country = "";

    @SerializedName("status")
    @Expose
    private String status = "";

    public String getBandName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }
}