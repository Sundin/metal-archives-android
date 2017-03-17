package se.kicksort.metalarchives.album;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.SongListEntryBinding;
import se.kicksort.metalarchives.model.Song;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class SongListEntry extends LinearLayout {
    private SongListEntryBinding binding;

    public SongListEntry(Context context) {
        this(context, null);
    }

    public SongListEntry(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.song_list_entry, this, true);
    }

    public void setSong(Song song) {
        binding.setSong(song);
    }
}
