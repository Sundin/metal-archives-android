package se.kicksort.metalarchives.model;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandMember implements SortedListAdapter.ViewModel {
    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("instrument")
    @Expose
    private String instrument = "";

    @SerializedName("years")
    @Expose
    private String years = "";

    public String getName() {
        if (name == null) {
            return "";
        }
        return name.replaceAll("&nbsp;", "");
    }

    public String getId() {
        return id;
    }

    public String getInstrument() {
        if (instrument == null) {
            return "";
        }
        return instrument.replaceAll("&nbsp;", "");
    }

    public String getYears() {
        if (years == null) {
            return "";
        }
        return years.replaceAll("&nbsp;", "");
    }

    @Override
    public <T> boolean isSameModelAs(T item) {
        if (item instanceof BandMember) {
            final BandMember other = (BandMember) item;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(T item) {
        if (item instanceof BandMember) {
            final BandMember other = (BandMember) item;
            if (!name.equals(other.name)) {
                return false;
            }
            if (!instrument.equals(other.instrument)) {
                return false;
            }
            return years != null ? years.equals(other.years) : other.years == null;
        }
        return false;
    }
}
