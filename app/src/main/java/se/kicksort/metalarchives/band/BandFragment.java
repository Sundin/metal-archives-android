package se.kicksort.metalarchives.band;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.NavigationManager;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.BandFragmentBinding;
import se.kicksort.metalarchives.model.Band;
import se.kicksort.metalarchives.model.BandMember;
import se.kicksort.metalarchives.model.TinyAlbum;
import se.kicksort.metalarchives.network.BandController;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class BandFragment extends Fragment {
    private BandFragmentBinding binding;
    private String bandName = null;
    private String bandId = null;
    private Band band;
    private BandController bandController = new BandController();

    private AlbumAdapter albumAdapter;
    private MembersAdapter membersAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    private int selectedDiscographySection = -1;

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

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    private void setupDiscographySection() {
        final Comparator<TinyAlbum> CHRONOLOGICAL_COMPARATOR = (a, b) -> a.getYear().compareTo(b.getYear());
        albumAdapter = new AlbumAdapter(getContext(), CHRONOLOGICAL_COMPARATOR);
        Disposable albumClicks = albumAdapter.getClicks().subscribe(album -> NavigationManager.getInstance().openAlbum(album.getId()));
        compositeDisposable.add(albumClicks);

        setupRecyclerView(binding.discographyRecyclerView, albumAdapter);

        binding.discographyToggle.setOnValueChangedListener(this::showDiscographySection);
    }

    private void setupMembersSection() {
        final Comparator<BandMember> ALPHABETICAL_COMPARATOR = (a, b) -> a.getName().compareTo(b.getName());
        membersAdapter = new MembersAdapter(getContext(), ALPHABETICAL_COMPARATOR);
        Disposable memberClicks = membersAdapter.getClicks().subscribe(member -> {
            // TODO: Clicked band member
        });
        compositeDisposable.add(memberClicks);

        setupRecyclerView(binding.membersRecyclerView, membersAdapter);

        binding.membersToggle.setOnValueChangedListener(this::showMembersSection);
        binding.membersToggle.setStates(new boolean[]{true, false, false});
    }

    private void setupRecyclerView(RecyclerView recyclerView, SortedListAdapter adapter) {
        recyclerView.addItemDecoration(new CustomDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void setBand(String bandName, String id) {
        this.bandName = bandName;
        this.bandId = id;
    }

    private void loadBandData() {
        Disposable loadBandRequest = bandController.getBand(bandName, bandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(band -> {
                    this.band = band;
                    binding.progressBar.setVisibility(View.GONE);
                    displayBand(band);
                }, error -> {
                    Log.d("ERROR", error.getMessage());
                });
        compositeDisposable.add(loadBandRequest);
    }

    private void displayBand(Band band) {
        binding.setBand(band);

        if (!band.getLogoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getLogoUrl()).into(binding.bandLogo);
        }

        if (!band.getPhotoUrl().equals("")) {
            Picasso.with(getContext()).load(band.getPhotoUrl()).into(binding.bandPhoto);
        }

        this.loadDiscography(band);

        membersAdapter.edit().replaceAll(band.getCurrentLineup()).commit();
    }

    private void loadDiscography(Band band) {
        if (selectedDiscographySection > -1) {
            showDiscographySection(selectedDiscographySection);
        } else if (band.getDiscography().size() >= 20) {
            // Show only full length albums
            binding.discographyToggle.setStates(new boolean[]{false, true, false, false, false});
            showDiscographySection(1);
        } else {
            binding.discographyToggle.setStates(new boolean[]{true, false, false, false, false});
            showDiscographySection(0);
        }
    }

    private void showDiscographySection(int position) {
        if (band == null) {
            Log.d("BandFragment", "Band data not loaded yet");
            return;
        }
        selectedDiscographySection = position;

        final ArrayList<TinyAlbum> fullDiscography = band.getDiscography();

        ArrayList<TinyAlbum> albumsToShow;

        String selectedSection = getResources().getStringArray(R.array.discography_array)[position];

        if (selectedSection.equalsIgnoreCase("main")) {
            String[] filter = {"full-length"};
            albumsToShow = filterAlbums(fullDiscography, filter);
        } else if (selectedSection.equalsIgnoreCase("live")) {
            String[] filter = {"live album", "video"};
            albumsToShow = filterAlbums(fullDiscography, filter);
        } else if (selectedSection.equalsIgnoreCase("demos")) {
            String[] filter = {"demo"};
            albumsToShow = filterAlbums(fullDiscography, filter);
        } else if (selectedSection.equalsIgnoreCase("misc")) {
            albumsToShow = fullDiscography;
            String[] filter = {"full-length", "live album", "video", "demo"};
            ArrayList<TinyAlbum> albumsToRemove = filterAlbums(fullDiscography, filter);
            albumsToShow.removeAll(albumsToRemove);
        } else {
            albumsToShow = fullDiscography;
        }

        if (albumsToShow.isEmpty()) {
            binding.discographyEmptyMessage.setVisibility(View.VISIBLE);
            binding.discographyRecyclerView.setVisibility(View.GONE);
        } else {
            binding.discographyEmptyMessage.setVisibility(View.GONE);
            binding.discographyRecyclerView.setVisibility(View.VISIBLE);
        }

        albumAdapter.edit().removeAll().commit();
        albumAdapter.edit().replaceAll(albumsToShow).commit();
    }

    private ArrayList<TinyAlbum> filterAlbums(ArrayList<TinyAlbum> fullDiscography, String[] types) {
        final ArrayList<TinyAlbum> filteredList = new ArrayList<>();

        for (TinyAlbum album : fullDiscography) {
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
            membersToShow = band.getCurrentLineup();
        } else if (selectedSection.equalsIgnoreCase("past")) {
            membersToShow = band.getAllBandMembers().getPastLineup();
        } else if (selectedSection.equalsIgnoreCase("live")) {
            membersToShow = band.getAllBandMembers().getLiveLineup();
        } else {
            membersToShow = band.getCurrentLineup();
        }

        if (membersToShow.isEmpty()) {
            binding.membersEmptyMessage.setVisibility(View.VISIBLE);
            binding.membersRecyclerView.setVisibility(View.GONE);
        } else {
            binding.membersEmptyMessage.setVisibility(View.GONE);
            binding.membersRecyclerView.setVisibility(View.VISIBLE);
        }

        membersAdapter.edit().removeAll().commit();
        membersAdapter.edit().replaceAll(membersToShow).commit();
    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
