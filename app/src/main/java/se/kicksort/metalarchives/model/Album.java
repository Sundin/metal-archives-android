package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Album {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("year")
    @Expose
    private String year;
}
