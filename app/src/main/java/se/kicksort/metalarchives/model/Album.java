package se.kicksort.metalarchives.model;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class Album implements SortedListAdapter.ViewModel {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("year")
    @Expose
    private String year;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    @Override
    public <T> boolean isSameModelAs(T item) {
        if (item instanceof Album) {
            final Album other = (Album) item;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(T item) {
        if (item instanceof Album) {
            final Album other = (Album) item;
            if (!id.equals(other.id)) {
                return false;
            }
            if (!title.equals(other.title)) {
                return false;
            }
            if (!type.equals(other.type)) {
                return false;
            }
            return year != null ? year.equals(other.year) : other.year == null;
        }
        return false;
    }
}
