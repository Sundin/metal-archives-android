package se.kicksort.metalarchives.band;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.model.Album;

public class AlbumViewHolder extends SortedListAdapter.ViewHolder<Album> {
    private final AlbumListEntryBinding mBinding;
    private PublishSubject<Album> clickSubject = PublishSubject.create();

    public AlbumViewHolder(AlbumListEntryBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.getRoot().setOnClickListener(view -> clickSubject.onNext(mBinding.getAlbum()));
    }

    @Override
    public void performBind(Album item) {
        mBinding.setAlbum(item);
    }

    public Observable<Album> getSearchResultClicks() {
        return clickSubject;
    }
}
