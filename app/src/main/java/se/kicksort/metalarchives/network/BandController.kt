package se.kicksort.metalarchives.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.ArrayList

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.kicksort.metalarchives.model.Band
import se.kicksort.metalarchives.model.BandSearchResult
import se.kicksort.metalarchives.model.SearchResults

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class BandController {
    private val baseUrl = Backend.baseUrl
    private val retrofit: Retrofit
    private val metalArchivesService: MetalArchivesService

    init {
        val gson = GsonBuilder()
                .registerTypeAdapter(Band::class.java, MyDeserializer<Band>())
                .registerTypeAdapter(SearchResults::class.java, MyDeserializer<SearchResults>())
                .create()

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build()
        metalArchivesService = retrofit.create(MetalArchivesService::class.java)
    }

    fun getBand(bandName: String, id: String): Observable<Band> {
        return metalArchivesService.getBand(bandName, id)
    }

    fun searchByBandName(query: String): Observable<ArrayList<BandSearchResult>> {
        return metalArchivesService.searchByBandName(query).map({ it ->  it.searchEntries })
    }
}
