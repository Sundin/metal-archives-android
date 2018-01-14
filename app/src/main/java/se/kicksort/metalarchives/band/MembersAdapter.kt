package se.kicksort.metalarchives.band

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

import java.util.Comparator

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.databinding.MemberListEntryBinding
import se.kicksort.metalarchives.model.BandMember

/**
 * Created by Gustav Sundin on 2017-03-31.
 */

class MembersAdapter(context: Context, comparator: Comparator<BandMember>) : SortedListAdapter<BandMember>(context, BandMember::class.java, comparator) {
    private val clickSubject = PublishSubject.create<BandMember>()
    private val mInflater: LayoutInflater

    val clicks: Observable<BandMember>
        get() = clickSubject

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): SortedListAdapter.ViewHolder<out BandMember> {
        val binding = MemberListEntryBinding.inflate(mInflater, parent, false)

        val viewHolder = MemberViewHolder(binding)
        viewHolder.clicks.subscribe { member -> clickSubject.onNext(member) }

        return viewHolder
    }
}
