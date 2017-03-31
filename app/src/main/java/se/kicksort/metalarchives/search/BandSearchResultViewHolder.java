package se.kicksort.metalarchives.search;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.BandSearchResultBinding;
import se.kicksort.metalarchives.model.BandSearchResult;

public class BandSearchResultViewHolder extends SortedListAdapter.ViewHolder<BandSearchResult> {
    private final BandSearchResultBinding mBinding;
    private PublishSubject<BandSearchResult> clickSubject = PublishSubject.create();

    public BandSearchResultViewHolder(BandSearchResultBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.getRoot().setOnClickListener(view -> clickSubject.onNext(mBinding.getModel()));
    }

    @Override
    public void performBind(BandSearchResult item) {
        mBinding.setModel(item);
    }

    public Observable<BandSearchResult> getSearchResultClicks() {
        return clickSubject;
    }
}