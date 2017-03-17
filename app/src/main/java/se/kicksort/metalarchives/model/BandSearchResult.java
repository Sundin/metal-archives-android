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
}
