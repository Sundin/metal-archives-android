package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 2017-07-07.
 */

class Label {
    @SerializedName("name")
    @Expose
    private val name = ""

    @SerializedName("id")
    @Expose
    private val id = ""

    fun getName(): String {
        return if (name == null) {
            ""
        } else name.replace("&nbsp;".toRegex(), "")
    }
}
