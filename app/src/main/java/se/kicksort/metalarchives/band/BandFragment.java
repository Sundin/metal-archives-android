package se.kicksort.metalarchives.band;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.NavigationManager;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.BandFragmentBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.BandMember;
import se.kicksort.metalarchives.network.BandController;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandFragment extends Fragment {
    private BandFragmentBinding binding;
    private String bandId = null;
    private BandController bandController = new BandController();

    private AlbumAdapter albumAdapter;

    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.band_fragment, container, false);

        if (bandId != null) {
            loadBandData();
        }

        final Comparator<Album> CHRONOLOGICAL_COMPARATOR = (a, b) -> a.getYear().compareTo(b.getYear());
        albumAdapter = new AlbumAdapter(getContext(), CHRONOLOGICAL_COMPARATOR);

        binding.discographyRecyclerView.addItemDecoration(new CustomDividerItemDecoration(getContext()));
        binding.discographyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.discographyRecyclerView.setAdapter(albumAdapter);

        albumAdapter.getClicks().subscribe(album -> NavigationManager.getInstance().openAlbum(album.getId()));

        binding.discographyToggle.setOnValueChangedListener(this::showDiscographySection);
        binding.membersToggle.setOnValueChangedListener(this::showMembersSection);

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

        albumAdapter.edit().replaceAll(band.getDiscography()).commit();

        for (BandMember member : band.getCurrentLineup()) {
            MemberListEntry memberView = new MemberListEntry(getContext());
            memberView.setMember(member);
            binding.membersSection.addView(memberView);

            View divider = new View(getContext());
            divider.setMinimumHeight(1);
            divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.membersSection.addView(divider);
        }
    }

    private void showDiscographySection(int position) {

    }

    private void showMembersSection(int position) {

    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
