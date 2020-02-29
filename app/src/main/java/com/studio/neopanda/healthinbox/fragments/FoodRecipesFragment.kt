package com.studio.neopanda.healthinbox.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.AddEditRecipeActivity
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.RecipesAdapter
import com.studio.neopanda.healthinbox.database.entities.Recipe
import com.studio.neopanda.healthinbox.database.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_food_recipes.*


class FoodRecipesFragment : Fragment() {
    private var recipesViewModel: RecipeViewModel? = null
    private val ADD_RECIPE_REQUEST = 1
    private val EDIT_RECIPE_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitFab()

        val recyclerView = recyclerview_recipes
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setHasFixedSize(true)

        val adapter = RecipesAdapter()
        recyclerView.adapter = adapter

        //OBSERVE RECIPES
        recipesViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        recipesViewModel!!.getAllRecipes().observe(
            this,
            Observer<List<Recipe>>(fun(recipes: List<Recipe>) {
                adapter.submitList(null)
                adapter.notifyDataSetChanged()
                adapter.submitList(recipes)
                adapter.notifyDataSetChanged()
                Toast.makeText(activity!!.applicationContext, "OBSERVING", Toast.LENGTH_SHORT)
                    .show()
            })
        )

        //EDIT ONE RECIPE
        adapter.setOnItemClickListener(object : RecipesAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val intent = Intent(activity, AddEditRecipeActivity::class.java)

                intent.putExtra(AddEditRecipeActivity.EXTRA_ID, recipe.id)
                intent.putExtra(AddEditRecipeActivity.EXTRA_NAME, recipe.name)
                intent.putExtra(AddEditRecipeActivity.EXTRA_PEOPLE, recipe.peopleSize)
                intent.putExtra(AddEditRecipeActivity.EXTRA_TIME, recipe.time)
                intent.putExtra(AddEditRecipeActivity.EXTRA_COST, recipe.cost)
                intent.putExtra(AddEditRecipeActivity.EXTRA_DIFFICULTY, recipe.difficulty)
                intent.putExtra(AddEditRecipeActivity.EXTRA_INGREDIENTS, recipe.ingredients)
                intent.putExtra(AddEditRecipeActivity.EXTRA_INSTRUCTIONS, recipe.instructions)

                startActivityForResult(intent, EDIT_RECIPE_REQUEST)
            }
        })

        //ADD ONE RECIPE
        val fabAddRecipe = fab_add_recipe
        fabAddRecipe.setOnClickListener {
            val intent = Intent(activity, AddEditRecipeActivity::class.java)
            startActivityForResult(intent, ADD_RECIPE_REQUEST)
        }

        //REMOVE ALL RECIPES
        val fabDeleteAllRecipes = fab_delete_all_recipes
        fabDeleteAllRecipes.setOnClickListener {
            recipesViewModel!!.deleteAllRecipes()
            Toast.makeText(activity, "Recipes nuked", Toast.LENGTH_SHORT).show()
        }

        //REMOVE ONE RECIPE
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recipesViewModel!!.delete(adapter.getRecipeAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Recipe deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_RECIPE_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(AddEditRecipeActivity.EXTRA_NAME)
            val people = data.getIntExtra(AddEditRecipeActivity.EXTRA_PEOPLE, 1)
            val time = data.getIntExtra(AddEditRecipeActivity.EXTRA_TIME, 1)
            val cost = data.getIntExtra(AddEditRecipeActivity.EXTRA_COST, 1)
            val difficulty = data.getIntExtra(AddEditRecipeActivity.EXTRA_DIFFICULTY, 1)
            val ingredients = data.getStringExtra(AddEditRecipeActivity.EXTRA_INGREDIENTS)
            val instructions = data.getStringExtra(AddEditRecipeActivity.EXTRA_INSTRUCTIONS)

            val recipe = Recipe(
                name!!,
                ingredients!!,
                instructions!!,
                people,
                difficulty,
                cost,
                time
            )
            recipesViewModel!!.insert(recipe)

            Toast.makeText(activity, "Recipe saved", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_RECIPE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data!!.getIntExtra(AddEditRecipeActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(activity, "Recipe cannot be saved", Toast.LENGTH_SHORT).show()
                return
            }

            val name = data!!.getStringExtra(AddEditRecipeActivity.EXTRA_NAME)
            val people = data.getIntExtra(AddEditRecipeActivity.EXTRA_PEOPLE, 1)
            val time = data.getIntExtra(AddEditRecipeActivity.EXTRA_TIME, 1)
            val cost = data.getIntExtra(AddEditRecipeActivity.EXTRA_COST, 1)
            val difficulty = data.getIntExtra(AddEditRecipeActivity.EXTRA_DIFFICULTY, 1)
            val ingredients = data.getStringExtra(AddEditRecipeActivity.EXTRA_INGREDIENTS)
            val instructions = data.getStringExtra(AddEditRecipeActivity.EXTRA_INSTRUCTIONS)

            val recipe = Recipe(
                name!!,
                ingredients!!,
                instructions!!,
                people,
                difficulty,
                cost,
                time
            )
            recipe.id = id
            recipesViewModel!!.insert(recipe)

            Toast.makeText(activity, "Recipe updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Recipe not updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setExitFab() {
        fab_exit_food_recipes.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
