package se.kicksort.metalarchives.band;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.model.Album;

public class AlbumViewHolder extends SortedListAdapter.ViewHolder<Album> {
    private final AlbumListEntryBinding binding;
    private PublishSubject<Album> clickSubject = PublishSubject.create();

    public AlbumViewHolder(AlbumListEntryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.getRoot().setOnClickListener(view -> clickSubject.onNext(this.binding.getAlbum()));
    }

    @Override
    public void performBind(Album item) {
        binding.setAlbum(item);
    }

    public Observable<Album> getClicks() {
        return clickSubject;
    }
}
