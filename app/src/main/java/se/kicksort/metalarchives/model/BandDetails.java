package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandDetails {
    @SerializedName("country of origin")
    @Expose
    private String country;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("formed in")
    @Expose
    private String formedIn;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("lyrical themes")
    @Expose
    private String lyricalThemes;

    @SerializedName("current label")
    @Expose
    private String currentLabel;

    @SerializedName("years active")
    @Expose
    private String yearsActive;
}
