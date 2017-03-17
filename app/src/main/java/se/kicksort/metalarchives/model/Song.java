package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class Song {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("length")
    @Expose
    private String length;

    public String getTitle() {
        return title;
    }

    public String getLength() {
        return length;
    }
}
