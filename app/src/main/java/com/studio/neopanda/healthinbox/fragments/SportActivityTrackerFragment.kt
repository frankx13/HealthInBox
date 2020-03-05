package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studio.neopanda.healthinbox.R
import kotlinx.android.synthetic.main.fragment_sport_activity_tracker.*

class SportActivityTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_activity_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setExitFab()
    }

    private fun setExitFab() {
        fab_exit_activity_tracker.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
