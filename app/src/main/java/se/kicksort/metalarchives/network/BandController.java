package se.kicksort.metalarchives.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.kicksort.metalarchives.model.Band;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandController {
    private final String baseUrl = "http://em.wemakesites.net/";
    private Retrofit retrofit;
    private MetalArchivesService metalArchivesService;

    public BandController() {
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(Band.class, new MyDeserializer<Band>())
                        .create();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
        metalArchivesService = retrofit.create(MetalArchivesService.class);
    }

    public Observable<Band> getBand(String band) {
        return metalArchivesService.getBand(band);
    }
}
