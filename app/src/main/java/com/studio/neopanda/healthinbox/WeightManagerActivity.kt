package com.studio.neopanda.healthinbox

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import com.studio.neopanda.healthinbox.database.entities.Weight
import com.studio.neopanda.healthinbox.database.viewmodels.WeightViewModel
import kotlinx.android.synthetic.main.activity_weight_manager.*
import android.widget.LinearLayout
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class WeightManagerActivity : BaseToolbarActivity() {

    private val targetActivity = MenuActivity()
    private var weightViewModel: WeightViewModel? = null

    private var weightValuesList: ArrayList<Int>? = null
    private var weightDatesList: ArrayList<String>? = null

    private lateinit var manager: LocalBroadcastManager

    private var isWeightFound = true

    private var averageTenDays = 0

    private var lastWeight = 0
    private var lastDate = "00-00-0000"

    private var comparator = 0

    //TODO : Transform the hardcoded reference into a SharedPreferences input
    private var referenceWeight = 75
    private var negativeReferenceWeight = 25f

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

            if (isWeightFound && weightValuesList!!.isNotEmpty()) {
                calculateAverageWeight()
                fillLastWeightValues()
                compareLastAndAverage()
                tv_weight_average.text = "Average 10 days\n" + averageTenDays + "Kg"
                tv_lweight_value.text = "Last weight : " + lastWeight + "kg"
                tv_lweight_date.text = "The : " + lastDate
                if (comparator == 1){
                    tv_lweight_value.setTextColor(Color.GREEN)
                    tv_lweight_value.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_trending_down_green_24dp,
                        0)
                } else {
                    tv_lweight_value.setTextColor(Color.RED)
                    tv_lweight_value.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_trending_up_red_24dp,
                        0)
                }
            }
        }
    }

    private fun compareLastAndAverage() {
        comparator = if (averageTenDays >= lastWeight){
            1
        } else {
            2
        }
    }

    private fun fillLastWeightValues() {
        lastWeight = weightValuesList!!.last()
        lastDate = weightDatesList!!.last()
    }

    private fun calculateAverageWeight() {
        for (i in 0 until weightValuesList!!.size) {
            averageTenDays += weightValuesList!![i]
        }
        averageTenDays /= weightValuesList!!.size

        adjustCourbToAverage()
    }

    private fun adjustCourbToAverage() {
        if (averageTenDays > referenceWeight){
            negativeReferenceWeight = averageTenDays - referenceWeight.toFloat()
            tv_nreference_weight.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                negativeReferenceWeight
            )
        }
    }
}
