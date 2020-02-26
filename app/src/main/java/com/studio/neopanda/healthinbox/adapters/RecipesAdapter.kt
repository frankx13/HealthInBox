package com.studio.neopanda.healthinbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Recipe

class RecipesAdapter : ListAdapter<Recipe, RecipesAdapter.RecipeHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)

        return RecipeHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val currentRecipe = getItem(position)
        holder.name.text = currentRecipe.name
        holder.numberPeople.text = currentRecipe.peopleSize.toString()
        holder.timePreparation.text = currentRecipe.time.toString() + "m"
        holder.cost.text = currentRecipe.cost.toString()
        holder.difficulty.text = currentRecipe.difficulty.toString()
        //TODO display all ingredients
        holder.ingredients.text = currentRecipe.ingredients
        holder.instructions.text = currentRecipe.instructions

        holder.displayHideInstructionsBtn.setOnClickListener {
            if (holder.instructions.visibility == View.VISIBLE){
                holder.instructions.visibility = View.GONE
                holder.displayHideInstructionsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add_black_24dp, 0)
                holder.displayHideInstructionsBtn.text = "Instructions : Show "
            } else {
                holder.instructions.visibility = View.VISIBLE
                holder.displayHideInstructionsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_indeterminate_check_box_black_24dp, 0)
                holder.displayHideInstructionsBtn.text = "Instructions : Hide "
            }
        }
    }

    fun getRecipeAt(position: Int): Recipe {
        return getItem(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    inner class RecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tv_recipe_name)
        val numberPeople: TextView = itemView.findViewById(R.id.tv_recipe_number_people)
        val timePreparation: TextView = itemView.findViewById(R.id.tv_recipe_time_preparation)
        val cost: TextView = itemView.findViewById(R.id.tv_recipe_cost)
        val difficulty: TextView = itemView.findViewById(R.id.tv_recipe_difficulty)
        val ingredients: TextView = itemView.findViewById(R.id.tv_recipe_ingredients_details)
        val instructions: TextView = itemView.findViewById(R.id.tv_recipe_instructions_details)
        val displayHideInstructionsBtn: Button = itemView.findViewById(R.id.btn_display_show_instructions)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(position))
                }
            }
        }

    }
}