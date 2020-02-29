package com.studio.neopanda.healthinbox.database

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.ref.WeakReference

class MealRepository(application: Application) {
    private val mealDao: MealDao
    private val allMeals: LiveData<List<Meal>>

    init {
        val database = AlimentDatabase.getInstance(application)
        mealDao = database.mealDao()
        allMeals = mealDao.allMeals
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

    fun getAllMealsStored(activity: Activity) {
        GetStoredMealsAsyncTask(mealDao, activity).execute()
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

    private class GetStoredMealsAsyncTask internal constructor(
        private val mealDao: MealDao,
        activity: Activity?
    ) :
        AsyncTask<Void, Void, Void>() {

        private var allMealsStored: List<Meal>? = null
        private var mealsName: ArrayList<String>? = null
        private var mealsDate: ArrayList<String>? = null
        private var mealsCalories: ArrayList<Int>? = null
        private val weakActivity: WeakReference<Activity> = WeakReference(activity!!)
        private var manager =
            LocalBroadcastManager.getInstance(weakActivity.get()!!.applicationContext)

        override fun doInBackground(vararg params: Void?): Void? {
            Log.e("PARAMS", "gregertsgesger $params")
            allMealsStored = ArrayList()
            mealsName = ArrayList()
            mealsDate = ArrayList()
            mealsCalories = ArrayList()

            allMealsStored = mealDao.allMealsStored()
            Log.e("MEALS", "" + allMealsStored!!)

            for (i in allMealsStored!!.indices) {
                mealsName!!.add(allMealsStored!![i].name)
                mealsDate!!.add(allMealsStored!![i].date)
                mealsCalories!!.add(allMealsStored!![i].calories)
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val intent = Intent("com.action.allmealsstatic")
            intent.putStringArrayListExtra("keyMealsNames", mealsName)
            intent.putStringArrayListExtra("keyMealsDates", mealsDate)
            intent.putIntegerArrayListExtra("keyMealsCalories", mealsCalories)
            manager.sendBroadcast(intent)
        }
    }
}