package se.kicksort.metalarchives.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gustav Sundin on 2017-04-28.
 */

public class AllBandMembers {
    @SerializedName("current")
    @Expose
    private ArrayList<BandMember> currentLineup;

    @SerializedName("past")
    @Expose
    private ArrayList<BandMember> pastLineup;

    @SerializedName("live")
    @Expose
    private ArrayList<BandMember> liveLineup;

    public ArrayList<BandMember> getCurrentLineup() {
        return new ArrayList<>(currentLineup);
    }
}
