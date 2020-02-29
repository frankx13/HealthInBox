package com.studio.neopanda.healthinbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.entities.Aliment


class AlimentAdapter : ListAdapter<Aliment, AlimentAdapter.AlimentHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aliment, parent, false)

        return AlimentHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlimentHolder, position: Int) {
        val currentAliment = getItem(position)
        holder.textViewName.text = currentAliment.name
        holder.textViewCalories.text = currentAliment.calories.toString() + " calories"
        holder.textViewWeight.text = "for " + currentAliment.weight.toString() + " g"
    }

    fun getAlimentAt(position: Int): Aliment {
        return getItem(position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(aliment: Aliment)
    }

    class DiffCallback : DiffUtil.ItemCallback<Aliment>() {
        override fun areItemsTheSame(oldItem: Aliment, newItem: Aliment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Aliment, newItem: Aliment): Boolean {
            return oldItem == newItem
        }
    }

    inner class AlimentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        val textViewCalories: TextView = itemView.findViewById(R.id.text_view_calories)
        val textViewWeight: TextView = itemView.findViewById(R.id.text_view_weight)

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