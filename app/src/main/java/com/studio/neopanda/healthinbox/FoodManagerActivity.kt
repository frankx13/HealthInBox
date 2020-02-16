package com.studio.neopanda.healthinbox

import android.os.Bundle
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_food_manager.*

class FoodManagerActivity : BaseToolbarActivity() {

    private val backTargetActivity = MenuActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar(toolbar_food_manager, backTargetActivity)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_food_manager
    }
}
