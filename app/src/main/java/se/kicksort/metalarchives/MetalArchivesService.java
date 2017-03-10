package se.kicksort.metalarchives;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public interface MetalArchivesService {
    @GET("band/{band}?api_key=2a97f42a-67f1-4ae2-a538-ea52277d2e63")
    Observable<Band> getBand(@Path("band") String band);
}
