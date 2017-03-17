package se.kicksort.metalarchives.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.SearchResultsFragmentBinding;
import se.kicksort.metalarchives.model.BandSearchResult;
import se.kicksort.metalarchives.network.BandController;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class SearchResultsView extends LinearLayout {
    private SearchResultsFragmentBinding binding;

    private BandController bandController = new BandController();
    private SearchAdapter mAdapter;
    private SearchViewQueryListener queryListener = new SearchViewQueryListener();
    private PublishSubject<BandSearchResult> clickSubject = PublishSubject.create();

    private static final Comparator<BandSearchResult> ALPHABETICAL_COMPARATOR = (a, b) -> a.getBandName().compareTo(b.getBandName());

    public SearchResultsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.search_results_fragment, this, true);

        mAdapter = new SearchAdapter(getContext(), ALPHABETICAL_COMPARATOR);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.getSearchResultClicks().subscribe(band -> {
            clickSubject.onNext(band);
            binding.recyclerView.setVisibility(View.INVISIBLE);
        });
    }

    public SearchResultsView(Context context) {
        this(context, null);
    }

    public Observable<BandSearchResult> getSearchResultClicks() {
        return clickSubject;
    }

    public SearchViewQueryListener getQueryListener() {
        return queryListener;
    }

    private class SearchViewQueryListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            handleSearch(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            handleSearch(query);
            return true;
        }

        private void handleSearch(String query) {
            binding.recyclerView.setVisibility(View.VISIBLE);

            mAdapter.removeAll();
            bandController.searchByBandName(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(results -> {
                        for (BandSearchResult band : results) {
                            mAdapter.add(band);
                        }
                    });

        }
    }
}
