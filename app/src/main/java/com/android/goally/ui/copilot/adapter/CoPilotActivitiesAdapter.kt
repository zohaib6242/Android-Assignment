package com.android.goally.ui.copilot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Activity
import com.android.goally.databinding.RowCopilotDetailBinding
import com.bumptech.glide.Glide

class CoPilotActivitiesAdapter(private val activities: List<Activity>) : RecyclerView.Adapter<CoPilotActivitiesAdapter.ActivityViewHolder>() {

    inner class ActivityViewHolder(private val binding: RowCopilotDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity) {
            binding.tvActivityDescription.text = activity.name
            Glide.with(binding.root)
                .load(activity.imgURL)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivActivityIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = RowCopilotDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}
