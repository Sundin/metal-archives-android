package se.kicksort.metalarchives.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.DetailedAlbum;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class AlbumController {
    private final String baseUrl = "http://em.wemakesites.net/";
    private Retrofit retrofit;
    private MetalArchivesService metalArchivesService;

    public AlbumController() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DetailedAlbum.class, new MyDeserializer<DetailedAlbum>())
                .create();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
        metalArchivesService = retrofit.create(MetalArchivesService.class);
    }

    public Observable<Album> getAlbum(String albumId) {
        return metalArchivesService.getAlbum(albumId).map(DetailedAlbum::getAlbum);
    }
}
