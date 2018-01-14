package se.kicksort.metalarchives.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Gustav Sundin on 13/03/17.
 */

class SearchResults {
    @SerializedName("query")
    @Expose
    private val query: String? = null

    @SerializedName("search_results")
    @Expose
    val searchEntries: ArrayList<BandSearchResult>? = null
}
