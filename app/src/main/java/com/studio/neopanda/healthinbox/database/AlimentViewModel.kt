package com.studio.neopanda.healthinbox.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AlimentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlimentRepository = AlimentRepository(application)

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

    fun searchAliments(search: String): List<Aliment> {
        return repository.searchAliments(search)
    }
}