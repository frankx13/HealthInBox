package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.studio.neopanda.healthinbox.R
import kotlinx.android.synthetic.main.fragment_data_all.*
import kotlinx.android.synthetic.main.fragment_data_categories.*

class DataCategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitFab()
    }

    private fun setExitFab() {
        fab_exit_food_data_categories.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
