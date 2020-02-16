package com.studio.neopanda.healthinbox

import android.os.Bundle
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import kotlinx.android.synthetic.main.activity_sport_manager.*


class SportManagerActivity : BaseToolbarActivity() {

    private val targetBackActivity = MenuActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar(toolbar_sport_manager, targetBackActivity)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_sport_manager
    }
}
