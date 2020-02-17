package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.FoodHistoryAdapter
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AppDatabase
import com.studio.neopanda.healthinbox.models.EntryFood
import kotlinx.android.synthetic.main.fragment_food_history.*

class FoodHistoryFragment : Fragment() {

    private var mListData : ArrayList<Aliment>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: FoodHistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        fetchAlimentsInDB()

        displayHistory()
        setExitFab()
    }

    private fun setExitFab() {
        fab_food_history.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

    private fun initUI() {
        mRecyclerView = food_history_rv
    }

    private fun fetchAlimentsInDB() {
        val db = AppDatabase(activity!!.applicationContext)
        db.alimentDao().getAll().forEach {
            mListData!!.add(Aliment(it.uid, it.name, it.calories))
        }
    }

    private fun displayHistory() {
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mAdapter = FoodHistoryAdapter(this.activity!!, mListData!!)
        mRecyclerView!!.adapter = mAdapter
    }
}
