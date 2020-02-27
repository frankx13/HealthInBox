package com.studio.neopanda.healthinbox.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


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

    fun searchAliments(search: String): List<Aliment> {
        val list: List<Aliment>? = null
        return list!!
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

    private class SearchAlimentsAsyncTask internal constructor(private val alimentDao: AlimentDao) :
        AsyncTask<String, Void, List<Aliment>>() {

        override fun doInBackground(vararg params: String?): List<Aliment> {
            return alimentDao.searchAliments(params[0])
        }
    }
}