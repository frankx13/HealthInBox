package com.studio.neopanda.healthinbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Aliment
import java.util.ArrayList

class AlimentAdapter : RecyclerView.Adapter<AlimentAdapter.AlimentHolder>() {
    private var aliments: List<Aliment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aliment, parent, false)

        return AlimentHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlimentHolder, position: Int) {
        val currentAliment = aliments[position]
        holder.textViewName.text = currentAliment.name
        holder.textViewCalories.setText(currentAliment.calories)
        holder.textViewWeight.text = currentAliment.weight.toString()
    }

    override fun getItemCount(): Int {
        return aliments.size
    }

    fun setAliments(aliments: List<Aliment>) {
        this.aliments = aliments
        notifyDataSetChanged()
    }

    fun getAlimentAt(position: Int): Aliment {
        return aliments[position]
    }

    inner class AlimentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        val textViewCalories: TextView = itemView.findViewById(R.id.text_view_calories)
        val textViewWeight: TextView = itemView.findViewById(R.id.text_view_weight)

    }
}