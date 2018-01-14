package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class AlbumWrapper {
    @SerializedName("band")
    @Expose
    private val band: Band? = null

    @SerializedName("album")
    @Expose
    val album: CompleteAlbumInfo? = null
}
