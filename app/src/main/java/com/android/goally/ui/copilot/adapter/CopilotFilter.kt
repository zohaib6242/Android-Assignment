package com.android.goally.ui.copilot.adapter

import android.widget.Filter
import com.android.goally.data.model.api.response.copilot.Routine

class CopilotFilter(
    private val originalList: List<Routine>,
    private val adapter: CopilotAdapter
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList: MutableList<Routine> = mutableListOf()

        if (constraint.isNullOrEmpty()) {
            filteredList.addAll(originalList) // No filter applied, return the original list
        } else {
            val filterPattern = constraint.toString().trim().lowercase()

            for (routine in originalList) {
                // Filter based on folder or schedule type
                if (routine.folder.lowercase().contains(filterPattern) ||
                    routine.scheduleV2?.type?.lowercase()?.contains(filterPattern) == true) {
                    filteredList.add(routine)
                }
            }
        }

        val results = FilterResults()
        results.values = filteredList
        return results
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.submitList(results?.values as List<Routine> )
    }
}
