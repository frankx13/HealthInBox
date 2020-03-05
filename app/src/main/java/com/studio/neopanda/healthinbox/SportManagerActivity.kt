package com.studio.neopanda.healthinbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import com.studio.neopanda.healthinbox.fragments.SportActivityTrackerFragment
import kotlinx.android.synthetic.main.activity_sport_manager.*

class SportManagerActivity : BaseToolbarActivity() {

    private val targetBackActivity = MenuActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar(toolbar_sport_manager, targetBackActivity)

        btn_stepcounter.setOnClickListener {
            val intent = Intent(this, StepCounterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_sport_manager
    }

    override fun onStart() {
        super.onStart()

        showFoodHistory()
    }

    private fun showFoodHistory() {
        activity_tracker_btn.setOnClickListener {
            val activityTracker = SportActivityTrackerFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.add(R.id.fragment_screen_sport_manager, activityTracker)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onBackPressed() {
        Log.e("STACK", supportFragmentManager.backStackEntryCount.toString())
        supportFragmentManager.popBackStackImmediate()
    }
}
