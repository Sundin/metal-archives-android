package se.kicksort.metalarchives.search;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import se.kicksort.metalarchives.databinding.BandSearchResultBinding;
import se.kicksort.metalarchives.model.BandSearchResult;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleViewHolder> {
    private PublishSubject<BandSearchResult> clickSubject = PublishSubject.create();

    public void add(BandSearchResult model) {
        mSortedList.add(model);
    }

    public void remove(BandSearchResult model) {
        mSortedList.remove(model);
    }

    public void add(List<BandSearchResult> models) {
        mSortedList.addAll(models);
    }

    public void remove(List<BandSearchResult> models) {
        mSortedList.beginBatchedUpdates();
        for (BandSearchResult model : models) {
            mSortedList.remove(model);
        }
        mSortedList.endBatchedUpdates();
    }

    public void removeAll() {
        mSortedList.clear();
    }

    public void replaceAll(List<BandSearchResult> models) {
        mSortedList.beginBatchedUpdates();
        for (int i = mSortedList.size() - 1; i >= 0; i--) {
            final BandSearchResult model = mSortedList.get(i);
            if (!models.contains(model)) {
                mSortedList.remove(model);
            }
        }
        mSortedList.addAll(models);
        mSortedList.endBatchedUpdates();
    }

    private final SortedList<BandSearchResult> mSortedList = new SortedList<>(BandSearchResult.class, new SortedList.Callback<BandSearchResult>() {
        @Override
        public int compare(BandSearchResult a, BandSearchResult b) {
            return mComparator.compare(a, b);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(BandSearchResult oldItem, BandSearchResult newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(BandSearchResult item1, BandSearchResult item2) {
            return item1.getId().equals(item2.getId());
        }
    });

    private final LayoutInflater mInflater;
    private final Comparator<BandSearchResult> mComparator;

    public ExampleAdapter(Context context, Comparator<BandSearchResult> comparator) {
        mInflater = LayoutInflater.from(context);
        mComparator = comparator;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BandSearchResultBinding binding = BandSearchResultBinding.inflate(mInflater, parent, false);
        return new ExampleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final BandSearchResult model = mSortedList.get(position);
        holder.bind(model);
        holder.itemView.setOnClickListener(view -> {
            int position1 = holder.getAdapterPosition();
            if (RecyclerView.NO_POSITION != position1) {
                BandSearchResult band = mSortedList.get(position1);
                clickSubject.onNext(band);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSortedList.size();
    }

    public Observable<BandSearchResult> getSearchResultClicks() {
        return clickSubject;
    }
}