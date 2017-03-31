package se.kicksort.metalarchives.band;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.AlbumListEntryBinding;
import se.kicksort.metalarchives.databinding.BandSearchResultBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.BandSearchResult;
import se.kicksort.metalarchives.search.BandSearchResultViewHolder;

public class AlbumAdapter extends SortedListAdapter<Album> {
    private PublishSubject<Album> clickSubject = PublishSubject.create();
    private final LayoutInflater mInflater;

    public AlbumAdapter(Context context, Comparator<Album> comparator) {
        super(context, Album.class, comparator);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected ViewHolder<? extends Album> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final AlbumListEntryBinding binding = AlbumListEntryBinding.inflate(mInflater, parent, false);

        AlbumViewHolder viewHolder = new AlbumViewHolder(binding);
        viewHolder.getSearchResultClicks().subscribe(bandSearchResult -> clickSubject.onNext(bandSearchResult));

        return viewHolder;
    }

    public Observable<Album> getClicks() {
        return clickSubject;
    }
}