package com.android.goally.ui.copilot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Activity
import com.android.goally.data.model.api.response.copilot.Routine
import com.android.goally.databinding.RowCopilotBinding
import com.android.goally.util.setSafeOnClickListener
import com.bumptech.glide.Glide
import kotlin.reflect.KFunction1

class CopilotAdapter(
    private var routines: List<Routine>,
    private val onCopilotClicked: (Routine) -> Unit
) : RecyclerView.Adapter<CopilotAdapter.CoPilotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoPilotViewHolder {
        val binding = RowCopilotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoPilotViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    override fun onBindViewHolder(holder: CoPilotViewHolder, position: Int) {
        val routine = routines[position]
        routine?.let {
            holder.bind(it)
        }
    }

    fun submitList(newRoutines: List<Routine>) {
        this.routines = newRoutines
        notifyDataSetChanged()
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

            // Setting click listener and passing the routine to the lambda
            binding.root.setSafeOnClickListener {
                onCopilotClicked(routine)
            }
        }
    }
}
