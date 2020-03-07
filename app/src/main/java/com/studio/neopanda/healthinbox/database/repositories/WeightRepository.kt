package com.studio.neopanda.healthinbox.database.repositories

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.studio.neopanda.healthinbox.database.AlimentDatabase
import com.studio.neopanda.healthinbox.database.dao.WeightDao
import com.studio.neopanda.healthinbox.database.entities.Weight
import java.lang.ref.WeakReference

class WeightRepository(application: Application) {
    private val weightDao: WeightDao
    private val allWeights: LiveData<List<Weight>>

    init {
        val database =
            AlimentDatabase.getInstance(
                application
            )
        weightDao = database.weightDao()
        allWeights = weightDao.allWeights
    }

    fun insert(weight: Weight) {
        InsertWeightAsyncTask(
            weightDao
        ).execute(weight)
    }

    fun update(weight: Weight) {
        UpdateWeightAsyncTask(
            weightDao
        ).execute(weight)
    }

    fun delete(weight: Weight) {
        DeleteWeightAsyncTask(
            weightDao
        ).execute(weight)
    }

    fun deleteAllWeights() {
        DeleteAllWeightsAsyncTask(
            weightDao
        ).execute()
    }

    fun getAllWeights(): LiveData<List<Weight>> {
        return allWeights
    }

    fun searchWeightsByDate(search: String, activity: Activity) {
        SearchWeightsAsyncTask(
            weightDao,
            search,
            activity
        ).execute()
    }

    private class InsertWeightAsyncTask internal constructor(private val weightDao: WeightDao) :
        AsyncTask<Weight, Void, Void>() {

        override fun doInBackground(vararg weights: Weight): Void? {
            weightDao.insert(weights[0])
            return null
        }
    }

    private class UpdateWeightAsyncTask internal constructor(private val weightDao: WeightDao) :
        AsyncTask<Weight, Void, Void>() {

        override fun doInBackground(vararg weights: Weight): Void? {
            weightDao.update(weights[0])
            return null
        }
    }

    private class DeleteWeightAsyncTask internal constructor(private val weightDao: WeightDao) :
        AsyncTask<Weight, Void, Void>() {

        override fun doInBackground(vararg weights: Weight): Void? {
            weightDao.delete(weights[0])
            return null
        }
    }

    private class DeleteAllWeightsAsyncTask internal constructor(private val weightDao: WeightDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            weightDao.deleteAllWeights()
            return null
        }
    }

    private class SearchWeightsAsyncTask internal constructor(
        private val weightDao: WeightDao,
        private val search: String,
        activity: Activity?
    ) :
        AsyncTask<Void, Void, Void>() {

        private var allWeightsStored: List<Weight>? = null
        private var weightsValue: ArrayList<Int>? = null
        private var weightsDate: ArrayList<String>? = null

        private val weakActivity: WeakReference<Activity> = WeakReference(activity!!)

        private var manager =
            LocalBroadcastManager.getInstance(weakActivity.get()!!.applicationContext)

        override fun doInBackground(vararg params: Void?): Void? {
            Log.e("PARAMS", "gregertsgesger $params")
            allWeightsStored = ArrayList()
            weightsValue = ArrayList()
            weightsDate = ArrayList()

            allWeightsStored = weightDao.searchWeightsByDate(search)
            Log.e("MEALS", "" + allWeightsStored!!)

            for (i in allWeightsStored!!.indices) {
                weightsValue!!.add(allWeightsStored!![i].value)
                weightsDate!!.add(allWeightsStored!![i].date)
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val intent = Intent("com.action.searchWeights")
            intent.putStringArrayListExtra("keyWeightsDates", weightsDate)
            intent.putIntegerArrayListExtra("keyWeightsValues", weightsValue)
            manager.sendBroadcast(intent)
        }
    }
}