package com.studio.neopanda.healthinbox.database.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.studio.neopanda.healthinbox.database.entities.Aliment
import com.studio.neopanda.healthinbox.database.repositories.AlimentRepository

class AlimentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlimentRepository =
        AlimentRepository(application)

    private var allAliments: LiveData<List<Aliment>>

    init {
        allAliments = repository.getAllAliments()
    }

    fun insert(aliment: Aliment) {
        repository.insert(aliment)
    }

    fun update(aliment: Aliment) {
        repository.update(aliment)
    }

    fun delete(aliment: Aliment) {
        repository.delete(aliment)
    }

    fun deleteAllAliments() {
        repository.deleteAllAliments()
    }

    fun getAllAliments(): LiveData<List<Aliment>> {
        return allAliments
    }

    fun searchAliments(search: String, activity: Activity) {
        repository.searchAliments(search, activity)
    }
}