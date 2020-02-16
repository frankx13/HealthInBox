package com.studio.neopanda.healthinbox.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.getLayoutContentViewID())
    }

    abstract fun getLayoutContentViewID(): Int

    //Toolbar setup to use in children
    protected fun configureToolbar(toolbar: Toolbar, targetActivity: AppCompatActivity) {
        toolbar.setOnClickListener {
            val intent = Intent(this, targetActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}