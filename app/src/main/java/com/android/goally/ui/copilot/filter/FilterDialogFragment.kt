package com.android.goally.ui.copilot.filter

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Routine

class FilterDialogFragment(
    private val routines: List<Routine>,
    private val onFilterSelected: (String) -> Unit
) : DialogFragment() {

    private lateinit var recyclerViewFilters: RecyclerView
    private lateinit var applyButton: Button
    private var selectedFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter_dialog, container, false)

        recyclerViewFilters = view.findViewById(R.id.recyclerViewFilters)
        applyButton = view.findViewById(R.id.applyButton)

        val folderMap = routines.groupBy { it.folder }.mapValues { it.value.size }

        val adapter = FilterAdapter(folderMap, onFilterSelected = {
            selectedFilter = it
        })
        recyclerViewFilters.layoutManager = LinearLayoutManager(context)
        recyclerViewFilters.adapter = adapter

        applyButton.setOnClickListener {
            selectedFilter?.let {
                onFilterSelected(it)
            }
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.3).toInt(),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.END)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent) // Transparent background
    }
}
