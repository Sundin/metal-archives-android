package se.kicksort.metalarchives;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.model.Album;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class AlbumListEntry extends LinearLayout {
    private AlbumListEntryBinding binding;

    public AlbumListEntry(Context context) {
        this(context, null);
    }

    public AlbumListEntry(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.album_list_entry, this, true);
    }

    public void setAlbum(Album album) {
        binding.setAlbum(album);
    }
}
