package se.kicksort.metalarchives.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import se.kicksort.metalarchives.model.AlbumWrapper
import se.kicksort.metalarchives.model.Band
import se.kicksort.metalarchives.model.CompleteAlbumInfo
import se.kicksort.metalarchives.model.SearchResults

/**
 * Created by Gustav Sundin on 10/03/17.
 */

interface MetalArchivesService {
    @GET("bands/{bandName}/{id}")
    fun getBand(@Path("bandName") bandName: String, @Path("id") id: String): Observable<Band>

    @GET("search/{query}")
    fun searchByBandName(@Path("query") query: String): Observable<SearchResults>

    @GET("albums/{albumId}")
    fun getAlbum(@Path("albumId") albumId: String): Observable<CompleteAlbumInfo>
}
