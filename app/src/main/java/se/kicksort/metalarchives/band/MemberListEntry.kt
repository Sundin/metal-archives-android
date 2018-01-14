package se.kicksort.metalarchives.band

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import se.kicksort.metalarchives.R
import se.kicksort.metalarchives.databinding.MemberListEntryBinding
import se.kicksort.metalarchives.model.BandMember

/**
 * Created by Gustav Sundin on 10/03/17.
 */

class MemberListEntry @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private val binding: MemberListEntryBinding

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.member_list_entry, this, true)
    }

    fun setMember(member: BandMember) {
        binding.member = member
    }
}
