package se.kicksort.metalarchives.album

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.R
import se.kicksort.metalarchives.band.MemberListEntry
import se.kicksort.metalarchives.databinding.AlbumFragmentBinding
import se.kicksort.metalarchives.model.BandMember
import se.kicksort.metalarchives.model.CompleteAlbumInfo
import se.kicksort.metalarchives.model.Song
import se.kicksort.metalarchives.network.AlbumController

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class AlbumFragment : Fragment() {
    private lateinit var binding: AlbumFragmentBinding

    private val albumController = AlbumController()
    private var albumId: String? = null

    private val compositeDisposable = CompositeDisposable()
    private val scrollSubject = PublishSubject.create<Int>()

    val scrollEvents: Observable<Int>
        get() = scrollSubject

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.album_fragment, container, false)

        if (albumId != null) {
            loadAlbumData()
        }

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener { scrollSubject.onNext(binding.scrollView.scrollY) }

        return binding.root
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun setAlbumId(id: String) {
        this.albumId = id
    }

    private fun loadAlbumData() {
        albumId?.let{
            val getAlbumRequest = albumController.getAlbum(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ album ->
                        binding.progressBar.visibility = View.GONE
                        displayAlbum(album)
                    }) { error -> Log.d("ERROR", error.message) }
            compositeDisposable.add(getAlbumRequest)
        }
    }

    private fun displayAlbum(album: CompleteAlbumInfo) {
        binding.album = album

        if (album.albumCoverUrl != "") {
            Picasso.with(context).load(album.albumCoverUrl).into(binding.albumArtwork)
        }

        for (song in album.songs!!) {
            val songView = SongListEntry(context)
            songView.setSong(song)
            binding.songSection.addView(songView)

            val divider = View(context)
            divider.minimumHeight = 1
            divider.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.songSection.addView(divider)
        }

        album.lineup!!.forEach { member ->
            val memberView = MemberListEntry(context)
            memberView.setMember(member)
            binding.membersSection.addView(memberView)

            val divider = View(context)
            divider.minimumHeight = 1
            divider.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.membersSection.addView(divider)
        }
    }
}
