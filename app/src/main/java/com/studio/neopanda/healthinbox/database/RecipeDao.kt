package com.studio.neopanda.healthinbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("DELETE FROM recipes_table")
    fun deleteAllRecipes()

    @get:Query("SELECT * FROM recipes_table ORDER BY name DESC")
    val allRecipes: LiveData<List<Recipe>>
}