package com.studio.neopanda.healthinbox.database.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.studio.neopanda.healthinbox.database.entities.Meal
import com.studio.neopanda.healthinbox.database.repositories.MealRepository

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MealRepository =
        MealRepository(application)

    private var allMeals: LiveData<List<Meal>>

    init {
        allMeals = repository.getAllMeals()
    }

    fun insert(meal: Meal) {
        repository.insert(meal)
    }

    fun update(meal: Meal) {
        repository.update(meal)
    }

    fun delete(meal: Meal) {
        repository.delete(meal)
    }

    fun deleteAllMeals() {
        repository.deleteAllMeals()
    }

    fun getAllMeals(): LiveData<List<Meal>> {
        return allMeals
    }

    fun getAllMealsStored(activity: Activity){
        repository.getAllMealsStored(activity)
    }
}