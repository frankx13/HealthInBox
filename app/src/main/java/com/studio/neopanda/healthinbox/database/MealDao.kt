package com.studio.neopanda.healthinbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(meal: Meal)

    @Update
    fun update(meal: Meal)

    @Delete
    fun delete(meal: Meal)

    @Query("DELETE FROM meals_table")
    fun deleteAllMeals()

    @get:Query("SELECT * FROM meals_table ORDER BY date DESC")
    val allMeals: LiveData<List<Meal>>
}