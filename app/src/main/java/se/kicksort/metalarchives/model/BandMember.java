package se.kicksort.metalarchives.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandMember {
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
        return name.replaceAll("&nbsp;","");
    }

    public String getId() {
        return id;
    }

    public String getInstrument() {
        if (instrument == null) {
            return "";
        }
        return instrument.replaceAll("&nbsp;","");
    }

    public String getYears() {
        if (years == null) {
            return "";
        }
        return years.replaceAll("&nbsp;","");
    }
}
