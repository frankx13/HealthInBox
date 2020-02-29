package com.studio.neopanda.healthinbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.entities.Meal

class MealsAdapter : ListAdapter<Meal, MealsAdapter.MealHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)

        return MealHolder(itemView)
    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val currentMeal = getItem(position)
        holder.name.text = currentMeal.name
        holder.date.text = currentMeal.date
        holder.calories.text = currentMeal.calories.toString()
    }

    fun getMealAt(position: Int): Meal {
        return getItem(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    class DiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    inner class MealHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tv_meal_name_input)
        val date: TextView = itemView.findViewById(R.id.tv_meal_date_input)
        val calories: TextView = itemView.findViewById(R.id.tv_meal_calories_input)

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