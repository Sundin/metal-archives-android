package se.kicksort.metalarchives;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.BandFragmentBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.Band;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandFragment extends Fragment {
    private BandFragmentBinding binding;
    private Band band;

    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();
    private boolean searchBarIsHidden = false;
    private int lastScrollY = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.band_fragment, container, false);
        binding.setBand(band);

        if (band != null) {
            binding.setBandDetails(band.getBandDetails());
            Picasso.with(getContext()).load(band.getLogoUrl()).into(binding.bandLogo);
            Picasso.with(getContext()).load(band.getPhotoUrl()).into(binding.bandPhoto);

            for (Album album : band.getDiscography()) {
                AlbumListEntry albumView = new AlbumListEntry(getContext());
                albumView.setAlbum(album);
                binding.discographySection.addView(albumView);

                View divider = new View(getContext());
                divider.setMinimumHeight(1);
                divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.discographySection.addView(divider);
            }
        }

        binding.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = binding.scrollView.getScrollY();
            int deltaY = scrollY - lastScrollY;

            if (deltaY > 0 && !searchBarIsHidden) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                searchBarIsHidden = true;
            } else if (deltaY < 0 && searchBarIsHidden) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                searchBarIsHidden = false;
            }

            lastScrollY = scrollY;
        });

        return binding.getRoot();
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}