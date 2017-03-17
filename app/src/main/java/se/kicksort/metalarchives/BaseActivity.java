package se.kicksort.metalarchives;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.roughike.bottombar.BottomBar;

import se.kicksort.metalarchives.band.BandFragment;
import se.kicksort.metalarchives.model.BandSearchResult;
import se.kicksort.metalarchives.search.SearchResultsView;

/**
 * Created by Gustav Sundin on 01/03/17.
 */

public class BaseActivity extends AppCompatActivity {
    public NavigationManager navigationManager;
    private SearchResultsView searchResults;

    private int lastScrollY = 0;
    private boolean searchBarIsHidden = false;

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

        searchResults = (SearchResultsView) findViewById(R.id.search_results);
        searchResults.getSearchResultClicks().subscribe(this::openBandResult);
    }

    private void openBandResult(BandSearchResult bandSearchResult) {
        hideKeyboard();
        openBand(bandSearchResult.getId());
    }

    private void openBand(String bandId) {
        BandFragment bandFragment = new BandFragment();
        bandFragment.setBandId(bandId);
        NavigationManager.getInstance().open(bandFragment);
        bandFragment.getScrollEvents().subscribe(this::handleScroll);
    }

    private void handleScroll(int scrollY) {
        int deltaY = scrollY - lastScrollY;

        if (deltaY > 0 && !searchBarIsHidden) {
            getSupportActionBar().hide();
            searchBarIsHidden = true;
        } else if (deltaY < 0 && searchBarIsHidden) {
            getSupportActionBar().show();
            searchBarIsHidden = false;
        }

        lastScrollY = scrollY;
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

        searchView.setOnQueryTextListener(searchResults.getQueryListener());

        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
