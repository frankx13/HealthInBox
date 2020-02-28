package com.studio.neopanda.healthinbox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Aliment

class DataSearchAdapter(private var mContext: Context, private var mData: List<Aliment>) : RecyclerView.Adapter<DataSearchAdapter.DataSearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataSearchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aliment, parent, false)

        return DataSearchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DataSearchViewHolder, position: Int) {
        holder.textViewName.text = mData[position].name
        holder.textViewCalories.text = mData[position].calories.toString()+ "calories"
        holder.textViewWeight.text = "for " + mData[position].weight.toString() + "g"
    }

    inner class DataSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        val textViewCalories: TextView = itemView.findViewById(R.id.text_view_calories)
        val textViewWeight: TextView = itemView.findViewById(R.id.text_view_weight)
    }
}