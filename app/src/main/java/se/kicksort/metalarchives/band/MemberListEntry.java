package se.kicksort.metalarchives.band;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import se.kicksort.metalarchives.R;
import se.kicksort.metalarchives.databinding.MemberListEntryBinding;
import se.kicksort.metalarchives.model.Album;
import se.kicksort.metalarchives.model.BandMember;

/**
 * Created by Gustav Sundin on 10/03/17.
 */

public class MemberListEntry extends LinearLayout {
    private MemberListEntryBinding binding;

    public MemberListEntry(Context context) {
        this(context, null);
    }

    public MemberListEntry(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.member_list_entry, this, true);
    }

    public void setMember(BandMember member) {
        binding.setMember(member);
    }
}
