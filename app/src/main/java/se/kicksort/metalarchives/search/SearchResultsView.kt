package se.kicksort.metalarchives.search

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView

import java.util.Comparator

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import se.kicksort.metalarchives.R
import se.kicksort.metalarchives.databinding.SearchResultsFragmentBinding
import se.kicksort.metalarchives.model.BandSearchResult
import se.kicksort.metalarchives.model.SearchResults
import se.kicksort.metalarchives.network.BandController

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class SearchResultsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private val binding: SearchResultsFragmentBinding

    private val bandController = BandController()
    private val mAdapter: SearchAdapter
    val queryListener = SearchViewQueryListener()
    private val clickSubject = PublishSubject.create<BandSearchResult>()

    val searchResultClicks: Observable<BandSearchResult>
        get() = clickSubject

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.search_results_fragment, this, true)

        mAdapter = SearchAdapter(getContext(), compareBy({ it.bandName }))

        binding.recyclerView.layoutManager = LinearLayoutManager(getContext())
        binding.recyclerView.adapter = mAdapter

        mAdapter.searchResultClicks.subscribe { band ->
            clickSubject.onNext(band)
            binding.recyclerView.visibility = View.INVISIBLE
        }
    }

    inner class SearchViewQueryListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            handleSearch(query)
            return true
        }

        override fun onQueryTextChange(query: String): Boolean {
            handleSearch(query)
            return true
        }

        private fun handleSearch(query: String) {
            binding.recyclerView.visibility = View.VISIBLE

            bandController.searchByBandName(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ bandResults -> mAdapter.edit().replaceAll(bandResults).commit() }) { error -> Log.d("ERROR", error.message) }
        }
    }
}
