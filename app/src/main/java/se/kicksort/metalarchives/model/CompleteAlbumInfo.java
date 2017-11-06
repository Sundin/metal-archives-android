package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class CompleteAlbumInfo extends TinyAlbum {
    @SerializedName("cover_url")
    @Expose
    private String albumCoverUrl;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("catalog_id")
    @Expose
    private String catalogId;

    @SerializedName("label")
    @Expose
    private Label label;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("limitation")
    @Expose
    private String limitation;

    @SerializedName("songs")
    @Expose
    private ArrayList<Song> songs;

    @SerializedName("lineup")
    @Expose
    private ArrayList<BandMember> lineup;

    public String getAlbumCoverUrl() {
        return albumCoverUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public String getLabelName() {
        return label.getName();
    }

    public String getFormat() {
        return format;
    }

    //TODO
    public String getReviews() {
        return "";
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<BandMember> getLineup() {
        return lineup;
    }
}
