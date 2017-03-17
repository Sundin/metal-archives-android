package se.kicksort.metalarchives;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.kicksort.metalarchives.databinding.StartFragmentBinding;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class StartFragment extends Fragment {
    private StartFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false);

        return binding.getRoot();
    }
}
