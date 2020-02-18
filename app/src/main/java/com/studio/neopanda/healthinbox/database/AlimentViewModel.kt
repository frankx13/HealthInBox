package com.studio.neopanda.healthinbox.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AlimentViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AlimentRepository =
        AlimentRepository(application)
    private var allAliments: LiveData<List<Aliments>> = repository.getAllAliments()

    fun insert(note: Aliments) {
        repository.insert(note)
    }

    fun deleteAllAliments() {
        repository.deleteAllAliments()
    }

    fun getAllAliments(): LiveData<List<Aliments>> {
        return allAliments
    }
}