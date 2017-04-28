package se.kicksort.metalarchives.band;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    private Band band;
    private BandController bandController = new BandController();

    private AlbumAdapter albumAdapter;
    private MembersAdapter membersAdapter;

    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.band_fragment, container, false);

        if (bandId != null) {
            loadBandData();
        }

        setupDiscographySection();
        setupMembersSection();

        binding.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> scrollSubject.onNext(binding.scrollView.getScrollY()));

        return binding.getRoot();
    }

    private void setupDiscographySection() {
        final Comparator<Album> CHRONOLOGICAL_COMPARATOR = (a, b) -> a.getYear().compareTo(b.getYear());
        albumAdapter = new AlbumAdapter(getContext(), CHRONOLOGICAL_COMPARATOR);
        albumAdapter.getClicks().subscribe(album -> NavigationManager.getInstance().openAlbum(album.getId()));

        setupRecyclerView(binding.discographyRecyclerView, albumAdapter);

        binding.discographyToggle.setOnValueChangedListener(this::showDiscographySection);
        binding.discographyToggle.setStates(new boolean[] {true, false, false, false, false});
    }

    private void setupMembersSection() {
        final Comparator<BandMember> ALPHABETICAL_COMPARATOR = (a, b) -> a.getName().compareTo(b.getName());
        membersAdapter = new MembersAdapter(getContext(), ALPHABETICAL_COMPARATOR);
        membersAdapter.getClicks().subscribe(member -> {
            // TODO: Clicked band member
        });

        setupRecyclerView(binding.membersRecyclerView, membersAdapter);

        binding.membersToggle.setOnValueChangedListener(this::showMembersSection);
        binding.membersToggle.setStates(new boolean[] {true, false, false});
    }

    private void setupRecyclerView(RecyclerView recyclerView, SortedListAdapter adapter) {
        recyclerView.addItemDecoration(new CustomDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void setBandId(String id) {
        this.bandId = id;
    }

    private void loadBandData() {
        bandController.getBand(bandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    this.band = band;
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    displayBand(band);
                });
    }

    private void displayBand(Band band) {
        binding.setBand(band);

        if (!band.getLogoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getLogoUrl()).into(binding.bandLogo);
        }

        if (!band.getPhotoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getPhotoUrl()).into(binding.bandPhoto);
        }

        if (band.getDiscography().size() >= 20) {
            // Show only full length albums
            binding.discographyToggle.setStates(new boolean[] {false, true, false, false, false});
            showDiscographySection(1);
        } else {
            albumAdapter.edit().replaceAll(band.getDiscography()).commit();
        }

        membersAdapter.edit().replaceAll(band.getCurrentLineup()).commit();
    }

    private void showDiscographySection(int position) {
        ArrayList<Album> albumsToShow;

        String selectedSection = getResources().getStringArray(R.array.discography_array)[position];

        if (selectedSection.equalsIgnoreCase("main")) {
            String[] filter = {"full-length"};
            albumsToShow = filterAlbums(filter);
        } else if (selectedSection.equalsIgnoreCase("live")) {
            String[] filter = {"live album", "video"};
            albumsToShow = filterAlbums(filter);
        } else if (selectedSection.equalsIgnoreCase("demos")) {
            String[] filter = {"demo"};
            albumsToShow = filterAlbums(filter);
        } else if (selectedSection.equalsIgnoreCase("misc")) {
            albumsToShow = band.getDiscography();
            String[] filter = {"full-length","live album", "video", "demo"};
            ArrayList<Album> albumsToRemove = filterAlbums(filter);
            albumsToShow.removeAll(albumsToRemove);
        } else {
            albumsToShow = band.getDiscography();
        }

        albumAdapter.edit().removeAll().commit();
        albumAdapter.edit().replaceAll(albumsToShow).commit();
    }

    private ArrayList<Album> filterAlbums(String[] types) {
        final ArrayList<Album> filteredList = new ArrayList<>();

        for (Album album : band.getDiscography()) {
            for (String type : types) {
                if (album.getType().equalsIgnoreCase(type)) {
                    filteredList.add(album);
                }
            }
        }

        return filteredList;
    }

    private void showMembersSection(int position) {
        ArrayList<BandMember> membersToShow;

        String selectedSection = getResources().getStringArray(R.array.members_array)[position];

        if (selectedSection.equalsIgnoreCase("current")) {
            String[] filter = {"current"};
            membersToShow = filterBandMembers(filter);
        } else if (selectedSection.equalsIgnoreCase("past")) {
            String[] filter = {"live album", "video"};
            membersToShow = filterBandMembers(filter);
        } else if (selectedSection.equalsIgnoreCase("live")) {
            String[] filter = {"demo"};
            membersToShow = filterBandMembers(filter);
        } else {
            membersToShow = band.getCurrentLineup();
        }

        membersAdapter.edit().removeAll().commit();
        membersAdapter.edit().replaceAll(membersToShow).commit();
    }

    private ArrayList<BandMember> filterBandMembers(String[] statuses) {
        final ArrayList<BandMember> filteredList = new ArrayList<>();

        for (BandMember member : band.getCurrentLineup()) {
            for (String status : statuses) {
                if (member.getStatus().equalsIgnoreCase(status)) {
                    filteredList.add(member);
                }
            }
        }

        return filteredList;
    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
