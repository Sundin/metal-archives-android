package se.kicksort.metalarchives.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 13/03/17.
 */

class BandSearchResult : SortedListAdapter.ViewModel {
    @SerializedName("_id")
    @Expose
    val id: String? = null

    @SerializedName("_score")
    @Expose
    val searchScore: Double? = null

    @SerializedName("_source")
    @Expose
    private val source: SearchResultsInnerData? = null

    val bandName: String?
        get() = source!!.bandName

    val genre: String?
        get() = source!!.genre

    val country: String?
        get() = source!!.country

    val status: String
        get() = source!!.status

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        return if (o !is BandSearchResult) false else isSameModelAs<Any>(o) && isContentTheSameAs<Any>(o)

    }

    override fun hashCode(): Int {
        var result = if (source!!.bandName != null) source.bandName!!.hashCode() else 0
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + if (source.genre != null) source.genre.hashCode() else 0
        result = 31 * result + if (source.country != null) source.country.hashCode() else 0

        return result
    }

    override fun <T> isSameModelAs(item: T): Boolean {
        if (item is BandSearchResult) {
            val other = item as BandSearchResult
            return other.id == id
        }
        return false
    }

    override fun <T> isContentTheSameAs(item: T): Boolean {
        if (item is BandSearchResult) {
            val other = item as BandSearchResult
            if (source!!.bandName != other.bandName) {
                return false
            }
            if (source.country != other.country) {
                return false
            }
            return if (source.genre != null) source.genre == other.genre else other.genre == null
        }
        return false
    }
}

internal class SearchResultsInnerData {
    @SerializedName("band_name")
    @Expose
    val bandName: String? = ""

    @SerializedName("genre")
    @Expose
    val genre: String? = ""

    @SerializedName("country")
    @Expose
    val country: String? = ""

    @SerializedName("status")
    @Expose
    val status = ""
}