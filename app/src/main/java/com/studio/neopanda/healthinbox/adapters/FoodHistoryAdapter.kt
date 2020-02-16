package com.studio.neopanda.healthinbox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.models.EntryFood

class FoodHistoryAdapter(context: Context, foodList: ArrayList<EntryFood>) :
    RecyclerView.Adapter<FoodHistoryAdapter.MyViewHolder>() {

    var mContext: Context? = null
    var mData: List<EntryFood>? = null

    init {
        this.mContext = context
        this.mData = foodList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_history, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.foodName.text = mData!![position].foodName
        holder.foodCalories.text = mData!![position].foodCalories.toString()
        holder.foodDate.text = mData!![position].foodDate
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var foodName: TextView = itemView.findViewById(R.id.item_food_history_name)
        internal var foodCalories: TextView = itemView.findViewById(R.id.item_food_history_calories)
        internal var foodDate: TextView = itemView.findViewById(R.id.item_food_history_date)
    }
}