package com.studio.neopanda.healthinbox.database

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import android.os.Debug
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.ref.WeakReference


class AlimentRepository(application: Application) {
    private val alimentDao: AlimentDao
    private val allAliments: LiveData<List<Aliment>>

    init {
        val database = AlimentDatabase.getInstance(application)
        alimentDao = database.alimentDao()
        allAliments = alimentDao.allAliments
    }

    fun insert(aliment: Aliment) {
        InsertAlimentAsyncTask(alimentDao).execute(aliment)
    }

    fun update(aliment: Aliment) {
        UpdateAlimentAsyncTask(alimentDao).execute(aliment)
    }

    fun delete(aliment: Aliment) {
        DeleteAlimentAsyncTask(alimentDao).execute(aliment)
    }

    fun deleteAllAliments() {
        DeleteAllAlimentsAsyncTask(alimentDao).execute()
    }

    fun getAllAliments(): LiveData<List<Aliment>> {
        return allAliments
    }

    fun searchAliments(search: String, activity: Activity) {
        SearchAlimentsAsyncTask(alimentDao, search, activity).execute()
    }

    private class InsertAlimentAsyncTask internal constructor(private val alimentDao: AlimentDao) :
        AsyncTask<Aliment, Void, Void>() {

        override fun doInBackground(vararg aliments: Aliment): Void? {
            alimentDao.insert(aliments[0])
            return null
        }
    }

    private class UpdateAlimentAsyncTask internal constructor(private val alimentDao: AlimentDao) :
        AsyncTask<Aliment, Void, Void>() {

        override fun doInBackground(vararg aliments: Aliment): Void? {
            alimentDao.update(aliments[0])
            return null
        }
    }

    private class DeleteAlimentAsyncTask internal constructor(private val alimentDao: AlimentDao) :
        AsyncTask<Aliment, Void, Void>() {

        override fun doInBackground(vararg aliments: Aliment): Void? {
            alimentDao.delete(aliments[0])
            return null
        }
    }

    private class DeleteAllAlimentsAsyncTask internal constructor(private val alimentDao: AlimentDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            alimentDao.deleteAllAliments()
            return null
        }
    }

    private class SearchAlimentsAsyncTask internal constructor(private val alimentDao: AlimentDao,
                                                               private val search: String,
                                                               activity: Activity?) :
        AsyncTask<Void, Void, Void>() {

        private var allAlimentsStored: List<Aliment>? = null
        private var alimentsName: ArrayList<String>? = null
        private var alimentsCalories: ArrayList<Int>? = null
        private val weakActivity: WeakReference<Activity> = WeakReference(activity!!)
        private var manager =
            LocalBroadcastManager.getInstance(weakActivity.get()!!.applicationContext)

        override fun doInBackground(vararg params: Void?): Void? {
            Debug.waitForDebugger()
            Log.e("PARAMS", "gregertsgesger $params")
            allAlimentsStored = ArrayList()
            alimentsName = ArrayList()
            alimentsCalories = ArrayList()

            allAlimentsStored = alimentDao.searchAliments(search)
            Log.e("MEALS", "" + allAlimentsStored!!)

            for (i in allAlimentsStored!!.indices) {
                alimentsName!!.add(allAlimentsStored!![i].name)
                alimentsCalories!!.add(allAlimentsStored!![i].calories)
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val intent = Intent("com.action.search")
            intent.putStringArrayListExtra("keyAlimentsNames", alimentsName)
            intent.putIntegerArrayListExtra("keyAlimentsCalories", alimentsCalories)
            manager.sendBroadcast(intent)
        }
    }
}