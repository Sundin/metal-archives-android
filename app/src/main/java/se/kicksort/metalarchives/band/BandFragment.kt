package se.kicksort.metalarchives.band

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.squareup.picasso.Picasso

import java.util.ArrayList
import java.util.Comparator

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.NavigationManager
import se.kicksort.metalarchives.R
import se.kicksort.metalarchives.databinding.BandFragmentBinding
import se.kicksort.metalarchives.model.Band
import se.kicksort.metalarchives.model.BandMember
import se.kicksort.metalarchives.model.TinyAlbum
import se.kicksort.metalarchives.network.BandController

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class BandFragment : Fragment() {
    private lateinit var binding: BandFragmentBinding
    private var bandName: String? = null
    private var bandId: String? = null
    private var band: Band? = null
    private val bandController = BandController()

    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var membersAdapter: MembersAdapter

    private val compositeDisposable = CompositeDisposable()
    private val scrollSubject = PublishSubject.create<Int>()

    val scrollEvents: Observable<Int>
        get() = scrollSubject

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.band_fragment, container, false)

        if (bandId != null) {
            loadBandData()
        }

        setupDiscographySection()
        setupMembersSection()

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener { scrollSubject.onNext(binding.scrollView.scrollY) }

        return binding.root
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun setupDiscographySection() {
        val CHRONOLOGICAL_COMPARATOR = Comparator<TinyAlbum> { a, b -> a.year!!.compareTo(b.year!!) }
        albumAdapter = AlbumAdapter(context, CHRONOLOGICAL_COMPARATOR)
        val albumClicks = albumAdapter.clicks.subscribe { album -> NavigationManager.instance.openAlbum(album.id!!) }
        compositeDisposable.add(albumClicks)

        setupRecyclerView(binding.discographyRecyclerView, albumAdapter)

        binding.discographyToggle.setOnValueChangedListener { this.showDiscographySection(it) }
        binding.discographyToggle.states = booleanArrayOf(true, false, false, false, false)
    }

    private fun setupMembersSection() {
        val ALPHABETICAL_COMPARATOR = Comparator<BandMember> { a, b -> a.getName().compareTo(b.getName()) }
        membersAdapter = MembersAdapter(context, ALPHABETICAL_COMPARATOR)
        val memberClicks = membersAdapter.clicks.subscribe { member ->
            // TODO: Clicked band member
        }
        compositeDisposable.add(memberClicks)

        setupRecyclerView(binding.membersRecyclerView, membersAdapter)

        binding.membersToggle.setOnValueChangedListener { this.showMembersSection(it) }
        binding.membersToggle.states = booleanArrayOf(true, false, false)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: SortedListAdapter<*>) {
        recyclerView.addItemDecoration(CustomDividerItemDecoration(context))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun setBand(bandName: String, id: String) {
        this.bandName = bandName
        this.bandId = id
    }

    private fun loadBandData() {
        val loadBandRequest = bandController.getBand(bandName!!, bandId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ band ->
                    this.band = band
                    binding.progressBar.visibility = View.GONE
                    displayBand(band)
                }) { error -> Log.d("ERROR", error.message) }
        compositeDisposable.add(loadBandRequest)
    }

    private fun displayBand(band: Band) {
        binding.band = band

        if (band.logoUrl != "") {
            Picasso.with(context).load(band.logoUrl).into(binding.bandLogo)
        }

        if (band.photoUrl != "") {
            Picasso.with(context).load(band.photoUrl).into(binding.bandPhoto)
        }

        if (band.discography.size >= 20) {
            // Show only full length albums
            binding.discographyToggle.states = booleanArrayOf(false, true, false, false, false)
            showDiscographySection(1)
        } else {
            albumAdapter.edit().replaceAll(band.discography).commit()
        }

        membersAdapter.edit().replaceAll(band.currentLineup).commit()
    }

    private fun showDiscographySection(position: Int) {
        val albumsToShow: ArrayList<TinyAlbum>

        val selectedSection = resources.getStringArray(R.array.discography_array)[position]

        if (selectedSection.equals("main", ignoreCase = true)) {
            val filter = arrayOf("full-length")
            albumsToShow = filterAlbums(filter)
        } else if (selectedSection.equals("live", ignoreCase = true)) {
            val filter = arrayOf("live album", "video")
            albumsToShow = filterAlbums(filter)
        } else if (selectedSection.equals("demos", ignoreCase = true)) {
            val filter = arrayOf("demo")
            albumsToShow = filterAlbums(filter)
        } else if (selectedSection.equals("misc", ignoreCase = true)) {
            albumsToShow = band!!.discography
            val filter = arrayOf("full-length", "live album", "video", "demo")
            val albumsToRemove = filterAlbums(filter)
            albumsToShow.removeAll(albumsToRemove)
        } else {
            albumsToShow = band!!.discography
        }

        if (albumsToShow.isEmpty()) {
            binding.discographyEmptyMessage.visibility = View.VISIBLE
            binding.discographyRecyclerView.visibility = View.GONE
        } else {
            binding.discographyEmptyMessage.visibility = View.GONE
            binding.discographyRecyclerView.visibility = View.VISIBLE
        }

        albumAdapter.edit().removeAll().commit()
        albumAdapter.edit().replaceAll(albumsToShow).commit()
    }

    private fun filterAlbums(types: Array<String>): ArrayList<TinyAlbum> {
        val filteredList = ArrayList<TinyAlbum>()

        for (album in band!!.discography) {
            for (type in types) {
                if (album.type!!.equals(type, ignoreCase = true)) {
                    filteredList.add(album)
                }
            }
        }

        return filteredList
    }

    private fun showMembersSection(position: Int) {
        val membersToShow: ArrayList<BandMember>

        val selectedSection = resources.getStringArray(R.array.members_array)[position]

        if (selectedSection.equals("current", ignoreCase = true)) {
            membersToShow = band!!.currentLineup
        } else if (selectedSection.equals("past", ignoreCase = true)) {
            membersToShow = band!!.allBandMembers!!.pastLineup!!
        } else if (selectedSection.equals("live", ignoreCase = true)) {
            membersToShow = band!!.allBandMembers!!.liveLineup!!
        } else {
            membersToShow = band!!.currentLineup
        }

        if (membersToShow.isEmpty()) {
            binding.membersEmptyMessage.visibility = View.VISIBLE
            binding.membersRecyclerView.visibility = View.GONE
        } else {
            binding.membersEmptyMessage.visibility = View.GONE
            binding.membersRecyclerView.visibility = View.VISIBLE
        }

        membersAdapter.edit().removeAll().commit()
        membersAdapter.edit().replaceAll(membersToShow).commit()
    }
}
