package com.studio.neopanda.healthinbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Aliments

class FoodHistoryAdapter : RecyclerView.Adapter<FoodHistoryAdapter.MyViewHolder>() {

    private var mData: List<Aliments>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_history, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mData == null) {
            0
        } else {
            mData!!.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.foodName.text = mData!![position].name
        holder.foodCalories.text = mData!![position].calories.toString()
    }

    fun setAliments(aliments: List<Aliments>) {
        this.mData = aliments
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var foodName: TextView = itemView.findViewById(R.id.item_food_history_name)
        internal var foodCalories: TextView = itemView.findViewById(R.id.item_food_history_calories)
    }
}