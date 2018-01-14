package se.kicksort.metalarchives.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.kicksort.metalarchives.model.AlbumWrapper
import se.kicksort.metalarchives.model.CompleteAlbumInfo

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class AlbumController {
    private val baseUrl = Backend.baseUrl
    private val retrofit: Retrofit
    private val metalArchivesService: MetalArchivesService

    init {
        val gson = GsonBuilder()
                .registerTypeAdapter(AlbumWrapper::class.java, MyDeserializer<AlbumWrapper>())
                .create()

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build()
        metalArchivesService = retrofit.create(MetalArchivesService::class.java!!)
    }

    fun getAlbum(albumId: String): Observable<CompleteAlbumInfo> {
        return metalArchivesService.getAlbum(albumId)
    }
}
