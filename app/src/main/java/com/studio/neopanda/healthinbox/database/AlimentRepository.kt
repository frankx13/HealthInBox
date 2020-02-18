package com.studio.neopanda.healthinbox.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import android.app.Application


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
}