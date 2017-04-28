package se.kicksort.metalarchives.band;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.model.TinyAlbum;

public class AlbumViewHolder extends SortedListAdapter.ViewHolder<TinyAlbum> {
    private final AlbumListEntryBinding binding;
    private PublishSubject<TinyAlbum> clickSubject = PublishSubject.create();

    public AlbumViewHolder(AlbumListEntryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.getRoot().setOnClickListener(view -> clickSubject.onNext(this.binding.getAlbum()));
    }

    @Override
    public void performBind(TinyAlbum item) {
        binding.setAlbum(item);
    }

    public Observable<TinyAlbum> getClicks() {
        return clickSubject;
    }
}
