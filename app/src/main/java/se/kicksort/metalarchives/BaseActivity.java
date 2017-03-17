package se.kicksort.metalarchives;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.roughike.bottombar.BottomBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.BandSearchResult;
import se.kicksort.metalarchives.network.BandController;

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

        /*bandController.getBand("3540327224")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    Log.d("BASE_", band.getBandName());
                    openBand(band);
                });*/
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

        SearchRecentSuggestions suggestions =
                new SearchRecentSuggestions(this,
                        SampleRecentSuggestionsProvider.AUTHORITY,
                        SampleRecentSuggestionsProvider.MODE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TAG", "onQueryTextSubmit ");
                bandController.searchByBandName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            for (BandSearchResult band : results) {
                                Log.d("TAG", band.getBandName());
                            }
                        });

                suggestions.saveRecentQuery(query, null);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("TAG", "onQueryTextChange ");

                return false;
            }
        });

        return true;
    }
}
