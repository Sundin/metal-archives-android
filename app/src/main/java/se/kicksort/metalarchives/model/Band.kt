package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class Band {
    @SerializedName("_id")
    @Expose
    private val id: String? = null

    @SerializedName("band_name")
    @Expose
    val bandName: String? = null

    @Expose
    private val country: String? = null

    @SerializedName("location")
    @Expose
    private val location: String? = null

    @SerializedName("status")
    @Expose
    private val status: String? = null

    @SerializedName("genre")
    @Expose
    val genre: String? = null

    @SerializedName("themes")
    @Expose
    private val lyricalThemes: String? = null

    @SerializedName("biography")
    @Expose
    private val biography: String? = null

    @SerializedName("label")
    @Expose
    private val label: Label? = null

    @SerializedName("formed_in")
    @Expose
    private val formedIn: String? = null

    @SerializedName("years_active")
    @Expose
    val yearsActive: String? = null

    @SerializedName("logo_url")
    @Expose
    val logoUrl: String? = null

    @SerializedName("photo_url")
    @Expose
    val photoUrl: String? = null

    @SerializedName("discography")
    @Expose
    public val discography = ArrayList<TinyAlbum>()

    @SerializedName("members")
    @Expose
    val allBandMembers: AllBandMembers? = null

    val currentLineup: ArrayList<BandMember>
        get() = allBandMembers!!.currentLineup!!

    val fullLocation: String
        get() = "$country ($location)"

    val labelName: String
        get() = label!!.getName()
}
