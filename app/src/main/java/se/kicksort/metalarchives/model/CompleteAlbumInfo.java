package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class CompleteAlbumInfo extends Album {
    @SerializedName("album_cover")
    @Expose
    private String albumCoverUrl;

    @SerializedName("release date")
    @Expose
    private String releaseDate;

    @SerializedName("catalog id")
    @Expose
    private String catalogId;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("reviews")
    @Expose
    private String reviews;

    @SerializedName("songs")
    @Expose
    private ArrayList<Song> songs;

    @SerializedName("personnel")
    @Expose
    private ArrayList<BandMember> lineup;
}
