package com.studio.neopanda.healthinbox

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import com.studio.neopanda.healthinbox.database.entities.Weight
import com.studio.neopanda.healthinbox.database.viewmodels.WeightViewModel
import kotlinx.android.synthetic.main.activity_weight_manager.*
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
    private var percentReference = 0.0f
    private var percentNeg = 0

    //TODO : Transform the hardcoded reference into a SharedPreferences input
    private var referenceWeight = 75
    private var negativeReferenceWeight = 25f

    private var newWeightValue = 0
    private var newWeightDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_manager)

        configureToolbar(toolbar_weight_manager, targetActivity)
        initBroadCastReceiver()

        weightValuesList = ArrayList()
        weightDatesList = ArrayList()

        weightViewModel = ViewModelProviders.of(this).get(WeightViewModel::class.java)
        weightViewModel!!.searchWeights(1, this)

        addWeightEntry()
        seeWeightEntries()
        cancelAddEntry()

        modifyReferenceWeight()
    }

    private fun modifyReferenceWeight() {
        tv_reference_weight.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if(event.rawX >= tv_reference_weight.right - tv_reference_weight.totalPaddingRight) {
                    weight_previous_to_current.visibility = View.GONE
                    et_reference_weight_input.visibility = View.VISIBLE
                    btn_reference_weight_validate.visibility = View.VISIBLE
                    btn_cancel_modify_rWeight.visibility = View.VISIBLE

                    validateNewReferenceWeight()
                    exitModification()

                    return@OnTouchListener true
                }
            }
            true
        })
    }

    private fun exitModification() {
        btn_cancel_modify_rWeight.setOnClickListener {
            weight_previous_to_current.visibility = View.VISIBLE
            et_reference_weight_input.visibility = View.GONE
            btn_reference_weight_validate.visibility = View.GONE
            btn_cancel_modify_rWeight.visibility = View.GONE
        }
    }

    private fun validateNewReferenceWeight() {
        btn_reference_weight_validate.setOnClickListener {
            //TODO : referenceWeight has to be saved into SharedPreferences
            referenceWeight = 0
            referenceWeight = et_reference_weight_input.editableText.toString().toInt()

            if (referenceWeight > 0){
                weight_previous_to_current.visibility = View.VISIBLE
                et_reference_weight_input.visibility = View.GONE
                btn_reference_weight_validate.visibility = View.GONE
                btn_cancel_modify_rWeight.visibility = View.GONE
                Toast.makeText(
                    this,
                    "The reference weight has been changed!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "You must fill a valid weight to save your entry!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun cancelAddEntry() {
        btn_cancel_add_weight.setOnClickListener {
            removeContainerNewEntry()
        }
    }

    private fun seeWeightEntries() {
        fab_history_weight.setOnClickListener {

        }
    }

    private fun addWeightEntry() {
        fab_add_weight.setOnClickListener {
            displayContainerNewEntry()

            addWeightToDB()
        }
    }

    private fun addWeightToDB() {
        add_entry_weight_btn.setOnClickListener {
            newWeightValue = et_weight_value_input.editableText.toString().toInt()
            newWeightDate = et_weight_date_input.editableText.toString()
            if (newWeightValue > 0 && newWeightDate.isNotEmpty()) {
                weightViewModel!!.insert(Weight(newWeightValue, newWeightDate))
                Toast.makeText(
                    this,
                    "Your weight has been added!",
                    Toast.LENGTH_SHORT
                ).show()
                removeContainerNewEntry()
            } else {
                Toast.makeText(
                    this,
                    "You must fill a valid weight and date to save your entry!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun displayContainerNewEntry() {
        tv_lweight_value.visibility = View.GONE
        tv_lweight_date.visibility = View.GONE
        tv_reference_weight.visibility = View.GONE
        weight_previous_to_current.visibility = View.GONE

        container_add_weight.visibility = View.VISIBLE

        newWeightValue = 0
        newWeightDate = ""
    }

    private fun removeContainerNewEntry() {
        tv_lweight_value.visibility = View.VISIBLE
        tv_lweight_date.visibility = View.VISIBLE
        tv_reference_weight.visibility = View.VISIBLE
        weight_previous_to_current.visibility = View.VISIBLE

        container_add_weight.visibility = View.GONE

        newWeightValue = 0
        newWeightDate = ""
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
                if (comparator == 1) {
                    tv_lweight_value.setTextColor(Color.GREEN)
                    tv_lweight_value.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_trending_down_green_24dp,
                        0
                    )
                } else {
                    tv_lweight_value.setTextColor(Color.RED)
                    tv_lweight_value.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_trending_up_red_24dp,
                        0
                    )
                }
            }
        }
    }

    private fun compareLastAndAverage() {
        comparator = if (averageTenDays >= lastWeight) {
            1
        } else {
            2
        }
    }

    private fun fillLastWeightValues() {
        lastWeight = weightValuesList!!.first()
        lastDate = weightDatesList!!.first()
    }

    private fun calculateAverageWeight() {
        for (i in 0 until weightValuesList!!.size) {
            averageTenDays += weightValuesList!![i]
        }
        averageTenDays /= weightValuesList!!.size

        adjustCourbToAverage()
    }

    private fun adjustCourbToAverage() {
        percentReference = referenceWeight / 100.0f

        if (averageTenDays > referenceWeight) {
            val percentNeg = averageTenDays.toFloat() - referenceWeight.toFloat()
            percentReference = percentNeg / percentReference

            //TODO : Replace the pos and neg values with screen dimensions instead
            tv_nreference_weight.height = percentReference.toInt()
        } else {
            tv_preference_weight.height = 100
        }
    }
}
