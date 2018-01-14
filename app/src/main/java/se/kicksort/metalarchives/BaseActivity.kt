package se.kicksort.metalarchives

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import se.kicksort.metalarchives.model.BandSearchResult
import se.kicksort.metalarchives.search.SearchResultsView

/**
 * Created by Gustav Sundin on 01/03/17.
 */

class BaseActivity : AppCompatActivity() {
    lateinit var navigationManager: NavigationManager
    private var searchResults: SearchResultsView? = null

    private val compositeDisposable = CompositeDisposable()

    private val lastScrollY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        navigationManager = NavigationManager(supportFragmentManager, this)
        val scrollEvents = navigationManager.scrollEvents.subscribe{ this.handleScroll(it) }
        compositeDisposable.add(scrollEvents)
        NavigationManager.instance.openStartView()

        searchResults = findViewById<View>(R.id.search_results) as SearchResultsView
        val searchResultClicks = searchResults!!.searchResultClicks.subscribe { this.openBandResult(it) }
        compositeDisposable.add(searchResultClicks)
        NavigationManager.instance.openBand("Armory", "3540372230")
    }

    public override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun handleScroll(scrollY: Int) {
        /* int deltaY = scrollY - lastScrollY;

        if (scrollY < 10 && deltaY < 0) {
            showSearchBar();
        } else if (scrollY > 20) {
            hideSearchBar();
        }

        lastScrollY = scrollY;*/
    }

    fun showSearchBar() {
        supportActionBar!!.show()
    }

    fun hideSearchBar() {
        supportActionBar!!.hide()
    }

    private fun openBandResult(bandSearchResult: BandSearchResult) {
        hideKeyboard()
        Log.d("Band", bandSearchResult.id)
        NavigationManager.instance.openBand(bandSearchResult.bandName!!, bandSearchResult.id!!)
    }

    override fun onBackPressed() {
        // Note: we are intentionally not calling the super implementation since we have built or own navigation manager
        navigationManager.navigateBack()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = false

        searchView.setOnQueryTextListener(searchResults!!.queryListener)

        return true
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
