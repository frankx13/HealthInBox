package com.studio.neopanda.healthinbox.database.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.studio.neopanda.healthinbox.database.AlimentDatabase
import com.studio.neopanda.healthinbox.database.dao.RecipeDao
import com.studio.neopanda.healthinbox.database.entities.Recipe

class RecipeRepository(application: Application) {
    private val recipeDao: RecipeDao
    private val allRecipes: LiveData<List<Recipe>>

    init {
        val database =
            AlimentDatabase.getInstance(
                application
            )
        recipeDao = database.recipeDao()
        allRecipes = recipeDao.allRecipes
    }

    fun insert(recipe: Recipe) {
        InsertRecipeAsyncTask(
            recipeDao
        ).execute(recipe)
    }

    fun update(recipe: Recipe) {
        UpdateRecipeAsyncTask(
            recipeDao
        ).execute(recipe)
    }

    fun delete(recipe: Recipe) {
        DeleteRecipeAsyncTask(
            recipeDao
        ).execute(recipe)
    }

    fun deleteAllRecipes() {
        DeleteAllRecipesAsyncTask(
            recipeDao
        ).execute()
    }

    fun getAllRecipes(): LiveData<List<Recipe>> {
        return allRecipes
    }

    private class InsertRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.insert(recipes[0])
            return null
        }
    }

    private class UpdateRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.update(recipes[0])
            return null
        }
    }

    private class DeleteRecipeAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Recipe, Void, Void>() {

        override fun doInBackground(vararg recipes: Recipe): Void? {
            recipeDao.delete(recipes[0])
            return null
        }
    }

    private class DeleteAllRecipesAsyncTask internal constructor(private val recipeDao: RecipeDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            recipeDao.deleteAllRecipes()
            return null
        }
    }
}