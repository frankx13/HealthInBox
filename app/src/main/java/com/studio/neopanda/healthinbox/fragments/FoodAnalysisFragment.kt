package com.studio.neopanda.healthinbox.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.studio.neopanda.healthinbox.CustomMarker
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.viewmodels.MealViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_food_analysis.*

class FoodAnalysisFragment : Fragment() {

    private lateinit var manager: LocalBroadcastManager
    private var mealViewModel: MealViewModel? = null
    private var mealsCaloriesList: ArrayList<Int>? = null
    private var mealsDatesList: ArrayList<String>? = null
    private var lineChart: LineChart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBroadCastReceiver()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealViewModel = ViewModelProviders.of(this).get(MealViewModel::class.java)
        mealViewModel!!.getAllMealsStored(activity!!)
        setExitBtn()
    }

    fun loadDataForChart(listCalories: ArrayList<Int>, listDates: ArrayList<String>) {
        //Convert ints to floats
        val listCaloriesFloat: ArrayList<Float> = ArrayList()
        for (i in 0 until listCalories.size) {
            listCaloriesFloat.add(listCalories[i].toFloat())
        }

        val entries = ArrayList<Entry>()

        if (listCaloriesFloat.size < 30) {
            var counter = 0f
            for (i in 0 until listCaloriesFloat.size) {
                entries.add(Entry(counter, listCaloriesFloat[i]))
                counter += 1
                Log.e("COUNTER", counter.toString())
            }

            //Create the parameters table of the chart
            val vl = LineDataSet(entries, "Calories")

            vl.setDrawValues(false)
            vl.setDrawFilled(true)
            vl.lineWidth = 3f
            vl.fillColor = Color.BLUE
            vl.fillAlpha = R.color.colorAccent
            vl.color = Color.WHITE
            vl.setCircleColor(Color.BLACK)
            vl.circleHoleColor = Color.CYAN
            vl.valueTextSize = 18f
            vl.valueTextColor = Color.BLACK

            lineChart = lineChartGraph
            //Set the rotation on the X axis
            lineChart!!.xAxis.labelRotationAngle = 0f

            //Add parameters to the chart
            lineChart!!.data = LineData(vl)

            //Assign the number of rows max
            val j = counter + 2f

            //Disable the right axis and apply the maxnumber of rows
            lineChart!!.axisRight.isEnabled = false
            lineChart!!.xAxis.axisMaximum = j + 0.1f

            //Allow Touch and Zoom events
            lineChart!!.setTouchEnabled(true)
            lineChart!!.setPinchZoom(true)

            //Add description to the chart
            lineChart!!.description.text = " Days ---------->"
            lineChart!!.description.textSize = 18f
            lineChart!!.description.textColor = Color.WHITE
            lineChart!!.setNoDataText("No data to analyze.")

            //Add animation to display the chart graphics
            lineChart!!.animateX(2200, Easing.EaseInExpo)

            //Add a marker to draw the chart
            val markerView = CustomMarker(activity!!, R.layout.marker_view)
            lineChart!!.marker = markerView
        }
    }

    private fun initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(context!!)
        val receiver = MyBroadCastReceiver()
        val filter = IntentFilter()
        filter.addAction("com.action.allmealsstatic")
        manager.registerReceiver(receiver, filter)
    }

    internal inner class MyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mealsDatesList = intent.getStringArrayListExtra("keyMealsDates")
            mealsCaloriesList = intent.getIntegerArrayListExtra("keyMealsCalories")
            Log.e("BROADCAST RECEIVED", "CaloriesList : " + mealsCaloriesList.toString())
            Log.e("BROADCAST RECEIVED", "DatesList : " + mealsDatesList.toString())

            clearFindViewByIdCache()
            loadDataForChart(mealsCaloriesList!!, mealsDatesList!!)
        }
    }

    private fun setExitBtn() {
        btn_exit_food_analysis.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
