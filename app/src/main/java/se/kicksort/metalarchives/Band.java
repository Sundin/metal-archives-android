package se.kicksort.metalarchives;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public String getBandName() {
        return bandName;
    }
}
