package se.kicksort.metalarchives.band

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.MemberListEntryBinding
import se.kicksort.metalarchives.model.BandMember

/**
 * Created by Gustav Sundin on 2017-03-31.
 */

class MemberViewHolder(private val binding: MemberListEntryBinding) : SortedListAdapter.ViewHolder<BandMember>(binding.root) {
    private val clickSubject = PublishSubject.create<BandMember>()

    val clicks: Observable<BandMember>
        get() = clickSubject

    init {
        this.binding.root.setOnClickListener { view -> clickSubject.onNext(this.binding.member) }
    }

    public override fun performBind(item: BandMember) {
        binding.member = item
    }
}