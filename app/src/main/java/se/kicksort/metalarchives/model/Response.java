package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Response {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("code")
    @Expose
    int code;

    @SerializedName("message")
    @Expose
    String message;
}
