package com.studio.neopanda.healthinbox.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MealRepository(application: Application) {
    private val mealDao: MealDao
    private val allMeals: LiveData<List<Meal>>
    private val allMealsStored: List<Meal>

    init {
        val database = AlimentDatabase.getInstance(application)
        mealDao = database.mealDao()
        allMeals = mealDao.allMeals
        allMealsStored = mealDao.allMealsStored
    }

    fun insert(meal: Meal) {
        InsertMealAsyncTask(mealDao).execute(meal)
    }

    fun update(meal: Meal) {
        UpdateMealAsyncTask(mealDao).execute(meal)
    }

    fun delete(meal: Meal) {
        DeleteMealAsyncTask(mealDao).execute(meal)
    }

    fun deleteAllMeals() {
        DeleteAllMealsAsyncTask(mealDao).execute()
    }

    fun getAllMeals(): LiveData<List<Meal>> {
        return allMeals
    }

    fun getAllMealsStored(): List<Meal> {
        return allMealsStored // Need to fetch it in AsyncTask
    }

    private class InsertMealAsyncTask internal constructor(private val mealDao: MealDao) :
        AsyncTask<Meal, Void, Void>() {

        override fun doInBackground(vararg meals: Meal): Void? {
            mealDao.insert(meals[0])
            return null
        }
    }

    private class UpdateMealAsyncTask internal constructor(private val mealDao: MealDao) :
        AsyncTask<Meal, Void, Void>() {

        override fun doInBackground(vararg meals: Meal): Void? {
            mealDao.update(meals[0])
            return null
        }
    }

    private class DeleteMealAsyncTask internal constructor(private val mealDao: MealDao) :
        AsyncTask<Meal, Void, Void>() {

        override fun doInBackground(vararg meals: Meal): Void? {
            mealDao.delete(meals[0])
            return null
        }
    }

    private class DeleteAllMealsAsyncTask internal constructor(private val mealDao: MealDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mealDao.deleteAllMeals()
            return null
        }
    }
}