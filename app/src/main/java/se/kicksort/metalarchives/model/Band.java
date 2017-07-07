package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Band {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("band_name")
    @Expose
    private String bandName;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("themes")
    @Expose
    private String lyricalThemes;

    @SerializedName("biography")
    @Expose
    private String biography;

    @SerializedName("label")
    @Expose
    private Label label;

    @SerializedName("formed_in")
    @Expose
    private String formedIn;

    @SerializedName("years_active")
    @Expose
    private String yearsActive;

    @SerializedName("logo_url")
    @Expose
    private String logoUrl;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("discography")
    @Expose
    private ArrayList<TinyAlbum> discography;

    @SerializedName("members")
    @Expose
    private AllBandMembers members;

    public String getBandName() {
        return bandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArrayList<TinyAlbum> getDiscography() {
        return new ArrayList<>(discography);
    }

    public ArrayList<BandMember> getCurrentLineup() {
        return members.getCurrentLineup();
    }

    public String getGenre() {
        return genre;
    }

    public String getFullLocation() {
        return country + " (" + location + ")";
    }

    public String getLabelName() {
        return label.getName();
    }

    public String getYearsActive() {
        return yearsActive;
    }
}
