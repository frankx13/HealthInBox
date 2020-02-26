package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.studio.neopanda.healthinbox.CustomMarker
import com.studio.neopanda.healthinbox.R
import kotlinx.android.synthetic.main.fragment_food_analysis.*


class FoodAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Part1
        val entries = ArrayList<Entry>()

        //Part2
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

        //Part3
        val vl = LineDataSet(entries, "My Type")

        //Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorPrimary
        vl.fillAlpha = R.color.colorAccent

        //Part5
        lineChart.xAxis.labelRotationAngle = 0f

        //Part6
        lineChart.data = LineData(vl)

        //Part7
        val j = 0.01f
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.axisMaximum = j+0.1f

        //Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        //Part9
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")

        //Part10
        lineChart.animateX(1800, Easing.EaseInExpo)

        //Part11
        val markerView = CustomMarker(activity!!, R.layout.marker_view)
        lineChart.marker = markerView
    }
}
