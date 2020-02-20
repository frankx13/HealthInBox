package com.studio.neopanda.healthinbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.studio.neopanda.healthinbox.base.BaseToolbarActivity
import com.studio.neopanda.healthinbox.fragments.FoodAnalysisFragment
import com.studio.neopanda.healthinbox.fragments.FoodDataFragment
import com.studio.neopanda.healthinbox.fragments.FoodHistoryFragment
import kotlinx.android.synthetic.main.activity_food_manager.*

class FoodManagerActivity : BaseToolbarActivity() {
    companion object {
        val ADD_NOTE_REQUEST = 1
        val EDIT_NOTE_REQUEST = 2
    }

    private val backTargetActivity = MenuActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar(toolbar_food_manager, backTargetActivity)
    }

    override fun getLayoutContentViewID(): Int {
        return R.layout.activity_food_manager
    }

    override fun onStart() {
        super.onStart()

        showFoodHistory()
        showFoodAnalysis()
        showFoodData()
    }

    private fun showFoodHistory() {
        food_history_btn.setOnClickListener {
            val historyFragment = FoodHistoryFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.add(R.id.fragment_screen, historyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun showFoodAnalysis() {
        food_analysis_btn.setOnClickListener {
            val analysisFragment = FoodAnalysisFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.add(R.id.fragment_screen, analysisFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun showFoodData() {
        food_aliments_data_btn.setOnClickListener {
            val dataFragment = FoodDataFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.add(R.id.fragment_screen, dataFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    // In your activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        for (fragment in supportFragmentManager.fragments) {
//            fragment.onActivityResult(requestCode, resultCode, data)
//        }
    }

    override fun onBackPressed() {
        Log.e("STACK", supportFragmentManager.backStackEntryCount.toString())
        supportFragmentManager.popBackStackImmediate()
    }
}
