package se.kicksort.metalarchives.album;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.AlbumFragmentBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.CompleteAlbumInfo;
import se.kicksort.metalarchives.network.AlbumController;

/**
 * Created by Gustav Sundin on 17/03/17.
 */

public class AlbumFragment extends Fragment {
    private AlbumFragmentBinding binding;

    private AlbumController albumController = new AlbumController();
    private String albumId = null;

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

    public void setAlbumId(String id) {
        this.albumId = id;
    }

    private void loadAlbumData() {
        albumController.getAlbum(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(album -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    displayAlbum(album);
                });
    }

    private void displayAlbum(CompleteAlbumInfo album) {
        binding.setAlbum(album);
    }
}
