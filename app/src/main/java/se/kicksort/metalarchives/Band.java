package se.kicksort.metalarchives;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Band {
    @SerializedName("data/id")
    @Expose
    private String id;

    @SerializedName("data/band_name")
    @Expose
    private String band_name;

    public String getBandName() {
        return band_name;
    }
}
