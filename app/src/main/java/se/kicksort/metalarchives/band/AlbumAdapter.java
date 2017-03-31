package se.kicksort.metalarchives.band;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.model.Album;

public class AlbumAdapter extends SortedListAdapter<Album> {
    private PublishSubject<Album> clickSubject = PublishSubject.create();
    private final LayoutInflater layoutInflater;

    public AlbumAdapter(Context context, Comparator<Album> comparator) {
        super(context, Album.class, comparator);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected ViewHolder<? extends Album> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final AlbumListEntryBinding binding = AlbumListEntryBinding.inflate(layoutInflater, parent, false);

        AlbumViewHolder viewHolder = new AlbumViewHolder(binding);
        viewHolder.getClicks().subscribe(band -> clickSubject.onNext(band));

        return viewHolder;
    }

    public Observable<Album> getClicks() {
        return clickSubject;
    }
}