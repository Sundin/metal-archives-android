package se.kicksort.metalarchives.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Gustav Sundin on 10/03/17.
 */

open class TinyAlbum : SortedListAdapter.ViewModel {
    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("_id")
    @Expose
    val id: String? = null

    @SerializedName("type")
    @Expose
    val type: String? = null

    @SerializedName("year")
    @Expose
    val year: String? = null

    override fun <T> isSameModelAs(item: T): Boolean {
        if (item is TinyAlbum) {
            val other = item as TinyAlbum
            return other.id == id
        }
        return false
    }

    override fun <T> isContentTheSameAs(item: T): Boolean {
        if (item is TinyAlbum) {
            val other = item as TinyAlbum
            if (id != other.id) {
                return false
            }
            if (title != other.title) {
                return false
            }
            if (type != other.type) {
                return false
            }
            return if (year != null) year == other.year else other.year == null
        }
        return false
    }
}
