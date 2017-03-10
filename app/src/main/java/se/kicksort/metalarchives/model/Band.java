package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Band {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("band_name")
    @Expose
    private String bandName;

    @SerializedName("details")
    @Expose
    private BandDetails details;

    @SerializedName("logo")
    @Expose
    private String logoUrl;

    @SerializedName("photo")
    @Expose
    private String photoUrl;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("discography")
    @Expose
    private ArrayList<Album> discography;

    @SerializedName("current_lineup")
    @Expose
    private ArrayList<BandMember> currentLineup;

    public String getBandName() {
        return bandName;
    }

    public BandDetails getBandDetails() {
        return details;
    }
}
