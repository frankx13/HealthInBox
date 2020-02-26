package com.studio.neopanda.healthinbox.fragments


import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.studio.neopanda.healthinbox.CustomMarker
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.AlimentDatabase
import com.studio.neopanda.healthinbox.database.Meal
import kotlinx.android.synthetic.main.fragment_food_analysis.*
import java.lang.ref.WeakReference


class FoodAnalysisFragment : Fragment() {

    private lateinit var manager: LocalBroadcastManager
    private var mealsCaloriesList: ArrayList<Int>? = null
    private var mealsDatesList: ArrayList<String>? = null

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

        //Create the values table
//        val entries = ArrayList<Entry>()

        AgentAsyncTask(activity).execute()

        //Fill values table with hardcoded values
        //TODO replace with DB data
//        entries.add(Entry(1f, 2855f))
//        entries.add(Entry(1f, 1850f))
//        entries.add(Entry(1f, 3950f))
//        entries.add(Entry(2f, 2355f))
//        entries.add(Entry(3f, 1950f))
//        entries.add(Entry(4f, 2130f))
//        entries.add(Entry(5f, 1780f))
//        entries.add(Entry(6f, 2250f))
//        entries.add(Entry(7f, 1985f))
//        entries.add(Entry(8f, 1995f))
//        entries.add(Entry(9f, 2450f))
//        entries.add(Entry(10f, 1500f))
//        entries.add(Entry(11f, 2500f))
//        entries.add(Entry(12f, 2000f))
//        entries.add(Entry(13f, 2350f))
//        entries.add(Entry(14f, 2955f))
//        entries.add(Entry(15f, 1785f))
//        entries.add(Entry(16f, 1815f))
//        entries.add(Entry(17f, 1965f))
//        entries.add(Entry(18f, 2450f))
//        entries.add(Entry(19f, 2780f))
//        entries.add(Entry(20f, 3150f))
//        entries.add(Entry(21f, 3050f))
//        entries.add(Entry(22f, 2585f))
//        entries.add(Entry(23f, 3350f))
//        entries.add(Entry(24f, 2450f))
//        entries.add(Entry(25f, 2100f))

        //Create the parameters table of the chart
//        val vl = LineDataSet(entries, "Calories")

        //Fill the parameters of the table
//        vl.setDrawValues(false)
//        vl.setDrawFilled(true)
//        vl.lineWidth = 3f
//        vl.fillColor = Color.BLUE
//        vl.fillAlpha = R.color.colorAccent
//        vl.color = Color.WHITE
//        vl.setCircleColor(Color.BLACK)
//        vl.circleHoleColor = Color.CYAN
//        vl.valueTextSize = 18f
//        vl.valueTextColor = Color.BLACK

//        //Set the rotation on the X axis
//        lineChart.xAxis.labelRotationAngle = 0f
//
//        //Add parameters to the chart
//        lineChart.data = LineData(vl)
//
//        //Assign the number of rows max
//        val j = 26f
//
//        //Disable the right axis and apply the maxnumber of rows
//        lineChart.axisRight.isEnabled = false
//        lineChart.xAxis.axisMaximum = j + 0.1f
//
//        //Allow Touch and Zoom events
//        lineChart.setTouchEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        //Add description to the chart
//        lineChart.description.text = " Days ---------->"
//        lineChart.description.textSize = 18f
//        lineChart.description.textColor = Color.WHITE
//        lineChart.setNoDataText("No data to analyze.")
//
//        //Add animation to display the chart graphics
//        lineChart.animateX(2200, Easing.EaseInExpo)
//
//        //Add a marker to draw the chart
//        val markerView = CustomMarker(activity!!, R.layout.marker_view)
//        lineChart.marker = markerView

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

            //Set the rotation on the X axis
            lineChart.xAxis.labelRotationAngle = 0f

            //Add parameters to the chart
            lineChart.data = LineData(vl)

            //Assign the number of rows max
            val j = counter+2f

            //Disable the right axis and apply the maxnumber of rows
            lineChart.axisRight.isEnabled = false
            lineChart.xAxis.axisMaximum = j + 0.1f

            //Allow Touch and Zoom events
            lineChart.setTouchEnabled(true)
            lineChart.setPinchZoom(true)

            //Add description to the chart
            lineChart.description.text = " Days ---------->"
            lineChart.description.textSize = 18f
            lineChart.description.textColor = Color.WHITE
            lineChart.setNoDataText("No data to analyze.")

            //Add animation to display the chart graphics
            lineChart.animateX(2200, Easing.EaseInExpo)

            //Add a marker to draw the chart
            val markerView = CustomMarker(activity!!, R.layout.marker_view)
            lineChart.marker = markerView
        }
    }

    private fun initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(context!!)
        val receiver = MyBroadCastReceiver()
        val filter = IntentFilter()
        //whatever
        filter.addAction("com.action.test")
        manager.registerReceiver(receiver, filter)
    }

    internal inner class MyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mealsCaloriesList = intent.getIntegerArrayListExtra("keyMealsCalories")
            mealsDatesList = intent.getStringArrayListExtra("keyMealsDates")
            Log.e("BROADCAST RECEIVED", "CaloriesList : " + mealsCaloriesList.toString())
            Log.e("BROADCAST RECEIVED", "DatesList : " + mealsDatesList.toString())

            loadDataForChart(mealsCaloriesList!!, mealsDatesList!!)
        }
    }

    private fun setExitBtn() {
        btn_exit_food_analysis.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

    class AgentAsyncTask(
        activity: Activity?
    ) : AsyncTask<Void, Void, Void>() {
        private var allMealsStored: List<Meal>? = null
        private var mealsCalories: ArrayList<Int>? = null
        private var mealsDates: ArrayList<String>? = null

        //Prevent leak
        private val weakActivity: WeakReference<Activity> = WeakReference(activity!!)

        private var manager =
            LocalBroadcastManager.getInstance(weakActivity.get()!!.applicationContext)

        override fun doInBackground(vararg params: Void): Void? {
            val database = AlimentDatabase.getInstance(weakActivity.get()!!.applicationContext)
            allMealsStored = ArrayList()
            mealsCalories = ArrayList()
            mealsDates = ArrayList()
            allMealsStored = database.mealDao().allMealsStored
            Log.e("MEALS", "" + allMealsStored!!)

            for (i in allMealsStored!!.indices) {
                mealsCalories!!.add(allMealsStored!![i].calories)
                mealsDates!!.add(allMealsStored!![i].date)
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val intent = Intent("com.action.test")
            intent.putIntegerArrayListExtra("keyMealsCalories", mealsCalories)
            intent.putStringArrayListExtra("keyMealsDates", mealsDates)
            manager.sendBroadcast(intent)
        }
    }
}
