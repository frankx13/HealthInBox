package com.studio.neopanda.healthinbox

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.studio.neopanda.healthinbox.adapters.DataSearchAdapter
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import com.studio.neopanda.healthinbox.database.entities.Aliment
import com.studio.neopanda.healthinbox.database.entities.Weight
import com.studio.neopanda.healthinbox.database.viewmodels.AlimentViewModel
import com.studio.neopanda.healthinbox.database.viewmodels.WeightViewModel
import kotlinx.android.synthetic.main.activity_weight_manager.*
import kotlinx.android.synthetic.main.fragment_data_search.*

class WeightManagerActivity : BaseToolbarActivity() {

    private val targetActivity = MenuActivity()
    private var weightViewModel: WeightViewModel? = null

    private lateinit var weightList: ArrayList<Weight>
    private var weightValuesList: ArrayList<Int>? = null
    private var weightDatesList: ArrayList<String>? = null

    private lateinit var manager: LocalBroadcastManager

    private var isWeightFound = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_manager)

        configureToolbar(toolbar_weight_manager, targetActivity)
        initBroadCastReceiver()

        weightValuesList = ArrayList()
        weightDatesList = ArrayList()

        weightViewModel = ViewModelProviders.of(this).get(WeightViewModel::class.java)
        weightViewModel!!.searchWeights(1, this)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_weight_manager
    }

    //INIT BR
    private fun initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(this)
        val receiver = MyBroadCastReceiver()
        val filter = IntentFilter()
        filter.addAction("com.action.searchWeights")
        manager.registerReceiver(receiver, filter)
    }

    //RECEIVE BROADCAST MESSAGE FROM REPOSITORY ASYNCTASK
    internal inner class MyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            weightDatesList = intent.getStringArrayListExtra("keyWeightsDates")
            weightValuesList = intent.getIntegerArrayListExtra("keyWeightsValues")
            Log.e("BROADCAST RECEIVED", "AlimNamesList : " + weightDatesList.toString())
            Log.e("BROADCAST RECEIVED", "AlimCalsList : " + weightValuesList.toString())

            if (weightDatesList!!.isNotEmpty() && weightValuesList!!.isNotEmpty()) {
                isWeightFound = true
            }

//            if (isWeightFound) {
//                tv_aliment_search_result.text =
//                    alimentsNamesList!!.size.toString() + " aliments found"
//                tv_aliment_search_result.visibility = View.VISIBLE
//            } else {
//                tv_aliment_search_result.text =
//                    "No results found in the database. Do you wish to add $alimentNameInputToStock as a new Aliment ?"
//                tv_aliment_search_result.visibility = View.VISIBLE
//                container_no_result_prompts.visibility = View.VISIBLE
//            }
        }
    }
}
