package se.kicksort.metalarchives;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.roughike.bottombar.BottomBar;

import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.BandSearchResult;
import se.kicksort.metalarchives.network.BandController;
import se.kicksort.metalarchives.search.ExampleAdapter;

/**
 * Created by Gustav Sundin on 01/03/17.
 */

public class BaseActivity extends AppCompatActivity {
    public NavigationManager navigationManager;
    private BandController bandController = new BandController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        BottomBar bottomMenu = (BottomBar) findViewById(R.id.bottom_menu);
        navigationManager = new NavigationManager(getSupportFragmentManager(), this);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        bottomMenu.setOnTabSelectListener(tabId -> {
            //
        });

        mAdapter = new ExampleAdapter(this, ALPHABETICAL_COMPARATOR);
        mAdapter.getSearchResultClicks().subscribe(this::openBandResult);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void openBandResult(BandSearchResult bandSearchResult) {
        bandController.getBand(bandSearchResult.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    recyclerView.setVisibility(View.INVISIBLE);
                    openBand(band);
                });
    }

    private void openBand(Band band) {
        BandFragment bandFragment = new BandFragment();
        bandFragment.setBand(band);
        NavigationManager.getInstance().open(bandFragment);
    }

    @Override
    public void onBackPressed() {
        // Note: we are intentionally not calling the super implementation since we have built or own navigation manager
        navigationManager.navigateBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setVisibility(View.VISIBLE);

                mAdapter.removeAll();
                bandController.searchByBandName(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            for (BandSearchResult band : results) {
                                mAdapter.add(band);
                            }
                        });

                return false;
            }
        });

        return true;
    }

    private static final Comparator<BandSearchResult> ALPHABETICAL_COMPARATOR = new Comparator<BandSearchResult>() {
        @Override
        public int compare(BandSearchResult a, BandSearchResult b) {
            return a.getBandName().compareTo(b.getBandName());
        }
    };

    private ExampleAdapter mAdapter;
    private List<BandSearchResult> mModels;
    private RecyclerView mRecyclerView;
}
