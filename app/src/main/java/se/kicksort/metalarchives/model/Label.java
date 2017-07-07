package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 2017-07-07.
 */

public class Label {
    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("id")
    @Expose
    private String id = "";

    public String getName() {
        if (name == null) {
            return "";
        }
        return name.replaceAll("&nbsp;", "");
    }
}
