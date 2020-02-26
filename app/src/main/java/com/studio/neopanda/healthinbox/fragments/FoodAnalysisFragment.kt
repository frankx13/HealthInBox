package com.studio.neopanda.healthinbox.fragments


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.studio.neopanda.healthinbox.CustomMarker
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AlimentDatabase
import com.studio.neopanda.healthinbox.database.Meal
import com.studio.neopanda.healthinbox.database.MealDao
import kotlinx.android.synthetic.main.fragment_food_analysis.*


class FoodAnalysisFragment : Fragment() {

    private lateinit var mealDao: MealDao
    private var allMeals: LiveData<List<Meal>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AlimentDatabase.getInstance(activity!!.applicationContext)
        mealDao = database.mealDao()
        allMeals = mealDao.allMeals

        Log.e("MEALS", "" + allMeals!!.value.toString())


        //Part1
        val entries = ArrayList<Entry>()

        //Part2
        entries.add(Entry(1f, 2855f))
        entries.add(Entry(1f, 1850f))
        entries.add(Entry(1f, 3950f))
        entries.add(Entry(2f, 2355f))
        entries.add(Entry(3f, 1950f))
        entries.add(Entry(4f, 2130f))
        entries.add(Entry(5f, 1780f))
        entries.add(Entry(6f, 2250f))
        entries.add(Entry(7f, 1985f))
        entries.add(Entry(8f, 1995f))
        entries.add(Entry(9f, 2450f))
        entries.add(Entry(10f, 1500f))
        entries.add(Entry(11f, 2500f))
        entries.add(Entry(12f, 2000f))
        entries.add(Entry(13f, 2350f))
        entries.add(Entry(14f, 2955f))
        entries.add(Entry(15f, 1785f))
        entries.add(Entry(16f, 1815f))
        entries.add(Entry(17f, 1965f))
        entries.add(Entry(18f, 2450f))
        entries.add(Entry(19f, 2780f))
        entries.add(Entry(20f, 3150f))
        entries.add(Entry(21f, 3050f))
        entries.add(Entry(22f, 2585f))
        entries.add(Entry(23f, 3350f))
        entries.add(Entry(24f, 2450f))
        entries.add(Entry(25f, 2100f))

        //Part3
        val vl = LineDataSet(entries, "Calories")

        //Part4
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

        //Part5
        lineChart.xAxis.labelRotationAngle = 0f

        //Part6
        lineChart.data = LineData(vl)

        //Part7
        val j = 30f
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.axisMaximum = j + 0.1f

        //Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        //Part9
        lineChart.description.text = " Days ---------->"
        lineChart.description.textSize = 18f
        lineChart.description.textColor = Color.WHITE
        lineChart.setNoDataText("No data to analyze.")

        //Part10
        lineChart.animateX(2200, Easing.EaseInExpo)

        //Part11
        val markerView = CustomMarker(activity!!, R.layout.marker_view)
        lineChart.marker = markerView

        setExitBtn()
    }

    private fun setExitBtn() {
        btn_exit_food_analysis.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
