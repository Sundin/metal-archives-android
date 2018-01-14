package se.kicksort.metalarchives.album

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import se.kicksort.metalarchives.R
import se.kicksort.metalarchives.databinding.SongListEntryBinding
import se.kicksort.metalarchives.model.Song

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class SongListEntry @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private val binding: SongListEntryBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.song_list_entry, this, true)
    }

    fun setSong(song: Song) {
        binding.song = song
    }
}
