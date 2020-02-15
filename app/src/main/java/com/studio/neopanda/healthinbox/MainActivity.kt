package com.studio.neopanda.healthinbox

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val IMAGE_REQUEST_CODE = 101
    private val ACCESS_TOKEN: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initData()
    }

    private fun initData() {
        //
    }

    private fun initUI() {
        toolbar?.setNavigationOnClickListener {
            Toast.makeText(applicationContext, "Navigation icon was clicked", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
