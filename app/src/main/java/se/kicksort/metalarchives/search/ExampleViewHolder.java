package se.kicksort.metalarchives.search;

import android.support.v7.widget.RecyclerView;

import se.kicksort.metalarchives.databinding.BandSearchResultBinding;
import se.kicksort.metalarchives.model.BandSearchResult;

public class ExampleViewHolder extends RecyclerView.ViewHolder {

    private final BandSearchResultBinding mBinding;

    public ExampleViewHolder(BandSearchResultBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(BandSearchResult item) {
        mBinding.setModel(item);
    }
}