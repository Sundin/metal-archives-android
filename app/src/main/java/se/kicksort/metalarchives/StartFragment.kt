package se.kicksort.metalarchives

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import se.kicksort.metalarchives.databinding.StartFragmentBinding

/**
 * Created by Gustav Sundin on 17/03/17.
 */

class StartFragment : Fragment() {
    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.start_fragment, container, false)

        return binding.root
    }
}
