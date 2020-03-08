package com.studio.neopanda.healthinbox.database.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.studio.neopanda.healthinbox.database.entities.Weight
import com.studio.neopanda.healthinbox.database.repositories.WeightRepository

class WeightViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WeightRepository =
        WeightRepository(application)

    private var allWeights: LiveData<List<Weight>>

    init {
        allWeights = repository.getAllWeights()
    }

    fun insert(weight: Weight) {
        repository.insert(weight)
    }

    fun update(weight: Weight) {
        repository.update(weight)
    }

    fun delete(weight: Weight) {
        repository.delete(weight)
    }

    fun deleteAllWeights() {
        repository.deleteAllWeights()
    }

    fun getAllWeights(): LiveData<List<Weight>> {
        return allWeights
    }

    fun searchWeights(searchId: Int, activity: Activity) {
        repository.searchWeightsByDate(searchId, activity)
    }
}