package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.AlimentAdapter
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AlimentViewModel
import kotlinx.android.synthetic.main.fragment_food_data.*


class FoodDataFragment : Fragment() {

    private var alimentViewModel: AlimentViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setHasFixedSize(true)

        val adapter = AlimentAdapter()
        recyclerView.adapter = adapter

        alimentViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
        alimentViewModel!!.getAllAliments().observe(this,
            Observer<List<Aliment>>(fun(aliments: List<Aliment>) {
                adapter.setAliments(aliments)
                Toast.makeText(activity!!.applicationContext, "OBSERVING", Toast.LENGTH_SHORT)
                    .show()
            })
        )
    }
}
