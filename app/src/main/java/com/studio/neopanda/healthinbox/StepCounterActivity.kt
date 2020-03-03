package com.studio.neopanda.healthinbox

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_step_counter.*

class StepCounterActivity : BaseToolbarActivity(), SensorEventListener {
    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_step_counter
    }

    private var running = false
    private var sensorManager: SensorManager? = null
    private val backTargetActivity = SportManagerActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar(toolbar_steps_tracker, backTargetActivity)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()

        running = true
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor", Toast.LENGTH_LONG).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()

        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            tv_steps.text = "" + event!!.values[0]
        }
    }
}