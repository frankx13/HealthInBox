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
import com.studio.neopanda.healthinbox.models.EntryFood
import kotlinx.android.synthetic.main.fragment_food_history.*

class FoodHistoryFragment : Fragment() {

    private var mListData : ArrayList<EntryFood>? = null
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

        initListData()
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

    private fun initListData() {
        //TODO : Replace hardcoded data with user meals
        mListData = ArrayList()
        mListData!!.add(EntryFood("Banana", 32, "16-02-2020"))
        mListData!!.add(EntryFood("Couscous", 85, "16-02-2020"))
        mListData!!.add(EntryFood("MacDonalds", 1265, "15-02-2020"))
        mListData!!.add(EntryFood("KFC", 952, "15-02-2020"))
        mListData!!.add(EntryFood("Porridge", 245, "14-02-2020"))
        mListData!!.add(EntryFood("Oatmeal", 92, "14-02-2020"))
    }

    private fun displayHistory() {
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mAdapter = FoodHistoryAdapter(this.activity!!, mListData!!)
        mRecyclerView!!.adapter = mAdapter
    }
}
