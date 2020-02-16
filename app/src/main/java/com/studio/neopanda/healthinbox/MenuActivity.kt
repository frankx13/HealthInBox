package com.studio.neopanda.healthinbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dropBoxBtn.setOnClickListener {
            val intent = Intent(this, LoginDropBoxActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
