package se.kicksort.metalarchives.band;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.MemberListEntryBinding;
import se.kicksort.metalarchives.model.BandMember;

/**
 * Created by Gustav Sundin on 2017-03-31.
 */

public class MembersAdapter extends SortedListAdapter<BandMember> {
    private PublishSubject<BandMember> clickSubject = PublishSubject.create();
    private final LayoutInflater mInflater;

    public MembersAdapter(Context context, Comparator<BandMember> comparator) {
        super(context, BandMember.class, comparator);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected SortedListAdapter.ViewHolder<? extends BandMember> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final MemberListEntryBinding binding = MemberListEntryBinding.inflate(mInflater, parent, false);

        MemberViewHolder viewHolder = new MemberViewHolder(binding);
        viewHolder.getClicks().subscribe(member -> clickSubject.onNext(member));

        return viewHolder;
    }

    public Observable<BandMember> getClicks() {
        return clickSubject;
    }
}
