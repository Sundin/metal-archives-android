package se.kicksort.metalarchives.search

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.BandSearchResultBinding
import se.kicksort.metalarchives.model.BandSearchResult

class BandSearchResultViewHolder(private val mBinding: BandSearchResultBinding) : SortedListAdapter.ViewHolder<BandSearchResult>(mBinding.root) {
    private val clickSubject = PublishSubject.create<BandSearchResult>()

    val searchResultClicks: Observable<BandSearchResult>
        get() = clickSubject

    init {
        mBinding.root.setOnClickListener { view -> clickSubject.onNext(mBinding.model) }
    }

    public override fun performBind(item: BandSearchResult) {
        mBinding.model = item
    }
}