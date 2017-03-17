package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 13/03/17.
 */

public class SearchResults {
    @SerializedName("query")
    @Expose
    private String query;

    @SerializedName("search_results")
    @Expose
    private ArrayList<BandSearchResult> searchResults;

    public ArrayList<BandSearchResult> getSearchEntries() {
        return searchResults;
    }
}
