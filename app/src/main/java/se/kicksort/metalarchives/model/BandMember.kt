package se.kicksort.metalarchives.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class BandMember : SortedListAdapter.ViewModel {
    @SerializedName("name")
    @Expose
    private val name = ""

    @SerializedName("_id")
    @Expose
    val id = ""

    @SerializedName("instrument")
    @Expose
    private val instrument = ""

    @SerializedName("years")
    @Expose
    private val years = ""

    val status = "current" // past / live

    fun getName(): String {
        return if (name == null) {
            ""
        } else name.replace("&nbsp;".toRegex(), "")
    }

    fun getInstrument(): String {
        return if (instrument == null) {
            ""
        } else instrument.replace("&nbsp;".toRegex(), "")
    }

    fun getYears(): String {
        return if (years == null) {
            ""
        } else years.replace("&nbsp;".toRegex(), "")
    }

    override fun <T> isSameModelAs(item: T): Boolean {
        if (item is BandMember) {
            val other = item as BandMember
            return other.id == id
        }
        return false
    }

    override fun <T> isContentTheSameAs(item: T): Boolean {
        if (item is BandMember) {
            val other = item as BandMember
            if (name != other.name) {
                return false
            }
            if (instrument != other.instrument) {
                return false
            }
            return if (years != null) years == other.years else other.years == null
        }
        return false
    }
}
