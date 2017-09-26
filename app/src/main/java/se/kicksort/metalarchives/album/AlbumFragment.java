package se.kicksort.metalarchives.album;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.band.MemberListEntry;
import se.kicksort.metalarchives.databinding.AlbumFragmentBinding;
import se.kicksort.metalarchives.model.BandMember;
import se.kicksort.metalarchives.model.CompleteAlbumInfo;
import se.kicksort.metalarchives.model.Song;
import se.kicksort.metalarchives.network.AlbumController;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class AlbumFragment extends Fragment {
    private AlbumFragmentBinding binding;

    private AlbumController albumController = new AlbumController();
    private String albumId = null;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final PublishSubject<Integer> scrollSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.album_fragment, container, false);

        if (albumId != null) {
            loadAlbumData();
        }

        binding.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> scrollSubject.onNext(binding.scrollView.getScrollY()));

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void setAlbumId(String id) {
        this.albumId = id;
    }

    private void loadAlbumData() {
        Disposable getAlbumRequest = albumController.getAlbum(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(album -> {
                    binding.progressBar.setVisibility(View.GONE);
                    displayAlbum(album);
                }, error -> {
                    Log.d("ERROR", error.getMessage());
                });
        compositeDisposable.add(getAlbumRequest);
    }

    private void displayAlbum(CompleteAlbumInfo album) {
        binding.setAlbum(album);

        if (!album.getAlbumCoverUrl().equals("")) {
            Picasso.with(getContext()).load(album.getAlbumCoverUrl()).into(binding.albumArtwork);
        }

        for (Song song : album.getSongs()) {
            SongListEntry songView = new SongListEntry(getContext());
            songView.setSong(song);
            binding.songSection.addView(songView);

            View divider = new View(getContext());
            divider.setMinimumHeight(1);
            divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.songSection.addView(divider);
        }

        for (BandMember member : album.getLineup()) {
            MemberListEntry memberView = new MemberListEntry(getContext());
            memberView.setMember(member);
            binding.membersSection.addView(memberView);

            View divider = new View(getContext());
            divider.setMinimumHeight(1);
            divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.membersSection.addView(divider);
        }
    }

    public Observable<Integer> getScrollEvents() {
        return scrollSubject;
    }
}
