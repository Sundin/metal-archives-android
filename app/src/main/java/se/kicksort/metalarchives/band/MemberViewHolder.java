package se.kicksort.metalarchives.band;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.MemberListEntryBinding;
import se.kicksort.metalarchives.model.BandMember;

/**
 * Created by Gustav Sundin on 2017-03-31.
 */

public class MemberViewHolder extends SortedListAdapter.ViewHolder<BandMember> {
    private final MemberListEntryBinding binding;
    private PublishSubject<BandMember> clickSubject = PublishSubject.create();

    public MemberViewHolder(MemberListEntryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.getRoot().setOnClickListener(view -> clickSubject.onNext(this.binding.getMember()));
    }

    @Override
    public void performBind(BandMember item) {
        binding.setMember(item);
    }

    public Observable<BandMember> getClicks() {
        return clickSubject;
    }
}