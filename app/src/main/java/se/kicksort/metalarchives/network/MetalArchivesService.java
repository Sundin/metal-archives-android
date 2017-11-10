package se.kicksort.metalarchives.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.kicksort.metalarchives.model.AlbumWrapper;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.CompleteAlbumInfo;
import se.kicksort.metalarchives.model.SearchResults;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public interface MetalArchivesService {
    @GET("bands/{bandName}/{id}")
    Observable<Band> getBand(@Path("bandName") String bandName, @Path("id") String id);

    @GET("search/{query}")
    Observable<SearchResults> searchByBandName(@Path("query") String query);

    @GET("albums/{albumId}")
    Observable<CompleteAlbumInfo> getAlbum(@Path("albumId") String albumId);
}
