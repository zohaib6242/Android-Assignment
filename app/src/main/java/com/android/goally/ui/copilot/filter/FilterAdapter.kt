package com.android.goally.ui.copilot.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R

class FilterAdapter(
    private val folderMap: Map<String, Int>,
    private val onFilterSelected: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return folderMap.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val folder = folderMap.keys.toList()[position]
        val count = folderMap[folder] ?: 0

        holder.radioButton.text = "$folder ($count)"
        holder.radioButton.isChecked = selectedPosition == position

        holder.radioButton.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onFilterSelected(folder)
        }
    }

    inner class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val radioButton: RadioButton = view.findViewById(R.id.radioButtonFilter)
    }
}
