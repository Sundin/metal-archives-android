package se.kicksort.metalarchives.band;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.NavigationManager;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.BandFragmentBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.network.BandController;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandFragment extends Fragment {
    private BandFragmentBinding binding;
    private String bandId = null;
    private BandController bandController = new BandController();

    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.band_fragment, container, false);

        if (bandId != null) {
            loadBandData();
        }

        binding.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> scrollSubject.onNext(binding.scrollView.getScrollY()));

        return binding.getRoot();
    }

    public void setBandId(String id) {
        this.bandId = id;
    }

    private void loadBandData() {
        bandController.getBand(bandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    displayBand(band);
                });
    }

    private void displayBand(Band band) {
        binding.setBand(band);
        binding.setBandDetails(band.getBandDetails());

        if (!band.getLogoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getLogoUrl()).into(binding.bandLogo);
        }
        if (!band.getPhotoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getPhotoUrl()).into(binding.bandPhoto);
        }

        for (Album album : band.getDiscography()) {
            AlbumListEntry albumView = new AlbumListEntry(getContext());
            albumView.setAlbum(album);
            albumView.setOnClickListener(view -> NavigationManager.getInstance().openAlbumFragment(album.getId()));
            binding.discographySection.addView(albumView);

            View divider = new View(getContext());
            divider.setMinimumHeight(1);
            divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.discographySection.addView(divider);
        }
    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
