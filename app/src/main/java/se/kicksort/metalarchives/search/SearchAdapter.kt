package se.kicksort.metalarchives.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import java.util.Comparator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.BandSearchResultBinding
import se.kicksort.metalarchives.model.BandSearchResult

class SearchAdapter(context: Context, comparator: Comparator<BandSearchResult>) : SortedListAdapter<BandSearchResult>(context, BandSearchResult::class.java, comparator) {
    private val clickSubject = PublishSubject.create<BandSearchResult>()
    private val mInflater: LayoutInflater

    val searchResultClicks: Observable<BandSearchResult>
        get() = clickSubject

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): SortedListAdapter.ViewHolder<out BandSearchResult> {
        val binding = BandSearchResultBinding.inflate(mInflater, parent, false)

        val viewHolder = BandSearchResultViewHolder(binding)
        viewHolder.searchResultClicks.subscribe { bandSearchResult -> clickSubject.onNext(bandSearchResult) }

        return viewHolder
    }
}