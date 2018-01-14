package se.kicksort.metalarchives.band

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import java.util.Comparator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding
import se.kicksort.metalarchives.model.TinyAlbum

class AlbumAdapter(context: Context, comparator: Comparator<TinyAlbum>) : SortedListAdapter<TinyAlbum>(context, TinyAlbum::class.java, comparator) {
    private val clickSubject = PublishSubject.create<TinyAlbum>()
    private val layoutInflater: LayoutInflater

    val clicks: Observable<TinyAlbum>
        get() = clickSubject

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): SortedListAdapter.ViewHolder<out TinyAlbum> {
        val binding = AlbumListEntryBinding.inflate(layoutInflater, parent, false)

        val viewHolder = AlbumViewHolder(binding)
        viewHolder.clicks.subscribe { band -> clickSubject.onNext(band) }

        return viewHolder
    }
}