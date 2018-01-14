package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class Song {
    @SerializedName("title")
    @Expose
    val title: String? = null

    //TODO
    val length: String
        get() = ""
}
