package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Gustav Sundin on 2017-04-28.
 */

class AllBandMembers {
    @SerializedName("current")
    @Expose
    val currentLineup: ArrayList<BandMember>? = null

    @SerializedName("past")
    @Expose
    val pastLineup: ArrayList<BandMember>? = null

    @SerializedName("live")
    @Expose
    val liveLineup: ArrayList<BandMember>? = null
}
