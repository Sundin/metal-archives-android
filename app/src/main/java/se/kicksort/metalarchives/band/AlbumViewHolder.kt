package se.kicksort.metalarchives.band

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding
import se.kicksort.metalarchives.model.TinyAlbum

class AlbumViewHolder(private val binding: AlbumListEntryBinding) : SortedListAdapter.ViewHolder<TinyAlbum>(binding.root) {
    private val clickSubject = PublishSubject.create<TinyAlbum>()

    val clicks: Observable<TinyAlbum>
        get() = clickSubject

    init {
        this.binding.root.setOnClickListener { view -> clickSubject.onNext(this.binding.album) }
    }

    public override fun performBind(item: TinyAlbum) {
        binding.album = item
    }
}
