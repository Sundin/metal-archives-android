package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class CompleteAlbumInfo : TinyAlbum() {
    @SerializedName("cover_url")
    @Expose
    val albumCoverUrl: String? = null

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null

    @SerializedName("catalog_id")
    @Expose
    val catalogId: String? = null

    @SerializedName("label")
    @Expose
    private val label: Label? = null

    @SerializedName("format")
    @Expose
    val format: String? = null

    @SerializedName("limitation")
    @Expose
    private val limitation: String? = null

    @SerializedName("songs")
    @Expose
    val songs: ArrayList<Song>? = null

    @SerializedName("lineup")
    @Expose
    val lineup: ArrayList<BandMember>? = null

    val labelName: String
        get() = label!!.getName()

    //TODO
    val reviews: String
        get() = ""
}
