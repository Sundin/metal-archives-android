package se.kicksort.metalarchives.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.BandSearchResultBinding;
import se.kicksort.metalarchives.model.BandSearchResult;

public class SearchAdapter extends SortedListAdapter<BandSearchResult> {
    private PublishSubject<BandSearchResult> clickSubject = PublishSubject.create();
    private final LayoutInflater mInflater;

    public SearchAdapter(Context context, Comparator<BandSearchResult> comparator) {
        super(context, BandSearchResult.class, comparator);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected ViewHolder<? extends BandSearchResult> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final BandSearchResultBinding binding = BandSearchResultBinding.inflate(mInflater, parent, false);

        BandSearchResultViewHolder viewHolder = new BandSearchResultViewHolder(binding);
        viewHolder.getSearchResultClicks().subscribe(bandSearchResult -> clickSubject.onNext(bandSearchResult));

        return viewHolder;
    }

    public Observable<BandSearchResult> getSearchResultClicks() {
        return clickSubject;
    }
}