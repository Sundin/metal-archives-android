package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class AlbumWrapper {
    @SerializedName("band")
    @Expose
    private Band band;

    @SerializedName("album")
    @Expose
    private CompleteAlbumInfo album;

    public CompleteAlbumInfo getAlbum() {
        return album;
    }
}
