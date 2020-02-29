package com.studio.neopanda.healthinbox.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studio.neopanda.healthinbox.database.entities.Meal

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

    @Query("SELECT * FROM meals_table ORDER BY date DESC")
    fun allMealsStored(): List<Meal>
}
