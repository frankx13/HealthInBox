package com.studio.neopanda.healthinbox

import android.content.Intent
import android.os.Bundle
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_weight_manager.*

class WeightManagerActivity : BaseToolbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_manager)

        configureToolbar(toolbar_weight_manager, this)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_weight_manager
    }
}
