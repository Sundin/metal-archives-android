package se.kicksort.metalarchives.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.kicksort.metalarchives.model.AlbumWrapper;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.SearchResults;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public interface MetalArchivesService {
    @GET("band/{band}")
    Observable<Band> getBand(@Path("band") String band);

    @GET("search/band_name/{query}")
    Observable<SearchResults> searchByBandName(@Path("query") String query);

    @GET("album/{albumId}")
    Observable<AlbumWrapper> getAlbum(@Path("albumId") String albumId);
}
