package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.FoodHistoryAdapter
import com.studio.neopanda.healthinbox.database.AlimentViewModel
import com.studio.neopanda.healthinbox.database.Aliments
import com.studio.neopanda.healthinbox.database.AppDatabase
import kotlinx.android.synthetic.main.fragment_food_history.*

class FoodHistoryFragment : Fragment() {

    private var mListData : List<Aliments>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: FoodHistoryAdapter? = null
    private lateinit var noteViewModel: AlimentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecyclerView()
        setExitFab()
    }

    private fun loadRecyclerView() {
        mListData = ArrayList()
        mRecyclerView = food_history_rv
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        mAdapter = FoodHistoryAdapter()
        mRecyclerView!!.adapter = mAdapter
        noteViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
        mListData = noteViewModel.getAllAliments().value

        noteViewModel.getAllAliments().observe(this,
            Observer<List<Aliments>> { t -> mAdapter!!.setAliments(t!!) })
    }

    private fun setExitFab() {
        fab_food_history.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
