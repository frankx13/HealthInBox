package com.studio.neopanda.healthinbox.database.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.studio.neopanda.healthinbox.database.entities.Recipe
import com.studio.neopanda.healthinbox.database.repositories.RecipeRepository

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository =
        RecipeRepository(application)

    private var allRecipes: LiveData<List<Recipe>>

    init {
        allRecipes = repository.getAllRecipes()
    }

    fun insert(recipe: Recipe) {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) {
        repository.delete(recipe)
    }

    fun deleteAllRecipes() {
        repository.deleteAllRecipes()
    }

    fun getAllRecipes(): LiveData<List<Recipe>> {
        return allRecipes
    }
}