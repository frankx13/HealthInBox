package com.studio.neopanda.healthinbox.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class AlimentRepository(application: Application) {

    private var alimentDAO: AlimentDAO

    private var allAliments: LiveData<List<Aliments>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        alimentDAO = database.alimentsDao()
        allAliments = alimentDAO.getAllAliments()
    }

    fun insert(aliment: Aliments) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(alimentDAO).execute(aliment)
    }

    fun deleteAllAliments() {
        val deleteAllNotesAsyncTask = DeleteAllAlimentsAsyncTask(
            alimentDAO
        ).execute()
    }

    fun getAllAliments(): LiveData<List<Aliments>> {
        return allAliments
    }

    private class InsertNoteAsyncTask(noteDao: AlimentDAO) : AsyncTask<Aliments, Unit, Unit>() {
        val noteDao = noteDao

        override fun doInBackground(vararg p0: Aliments?) {
            noteDao.insert(p0[0]!!)
        }
    }


    private class DeleteAllAlimentsAsyncTask(val noteDao: AlimentDAO) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            noteDao.deleteAllAliments()
        }
    }

}