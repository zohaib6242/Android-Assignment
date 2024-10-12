package com.android.goally.ui.copilot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.databinding.RowCopilotBinding
import com.android.goally.databinding.ShimmerRowCopilotBinding
import com.android.goally.util.setSafeOnClickListener
import com.bumptech.glide.Glide

import android.widget.Filter
import android.widget.Filterable

class CopilotAdapter(
    private var routines: List<Routine>,
    private val onCopilotClicked: (Routine) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_SHIMMER = 2
    private var isLoading = true // Track loading state

    private var originalRoutines: List<Routine> = routines.toList()

    // Reference to the filter
    private var filter: CopilotFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = RowCopilotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CoPilotViewHolder(binding)
        } else {
            val binding = ShimmerRowCopilotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ShimmerViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 10 else routines.size // Show 10 shimmer items when loading
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CoPilotViewHolder) {
            val routine = routines[position]
            holder.bind(routine)
        } else if (holder is ShimmerViewHolder) {
            holder.startShimmer()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    fun submitList(newRoutines: List<Routine>) {
        this.originalRoutines = newRoutines
        this.routines = newRoutines
        this.isLoading = false
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = CopilotFilter(originalRoutines, this) // Initialize the custom filter
        }
        return filter as CopilotFilter
    }

    inner class CoPilotViewHolder(private val binding: RowCopilotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(routine: Routine) {
            binding.tvCopilotDescription.text = routine.name
            binding.tvCopilotFolder.text = routine.folder

            // Check for null scheduleV2 and assign the text accordingly
            routine.scheduleV2?.let {
                binding.tvCopilotSchedule.text = it.type
            } ?: run {
                binding.tvCopilotSchedule.text = "No Schedule"
            }

            Glide.with(binding.root)
                .load(routine.imgURL)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivCopilotIcon)

            binding.root.setSafeOnClickListener {
                onCopilotClicked(routine)
            }
        }
    }

    inner class ShimmerViewHolder(private val binding: ShimmerRowCopilotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun startShimmer() {
            binding.shimmerViewContainer.startShimmer()
        }
    }

}
