package se.kicksort.metalarchives;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.kicksort.metalarchives.databinding.BandFragmentBinding;
import se.kicksort.metalarchives.model.Band;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandFragment extends Fragment {
    private BandFragmentBinding binding;
    private Band band;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.band_fragment, container, false);
        binding.setBand(band);
        binding.setBandDetails(band.getBandDetails());

        return binding.getRoot();
    }

    public void setBand(Band band) {
        this.band = band;
    }
}
