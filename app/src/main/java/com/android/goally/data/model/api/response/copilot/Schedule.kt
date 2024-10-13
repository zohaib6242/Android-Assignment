package com.android.goally.data.model.api.response.copilot

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Added primary key
    val type: String
)
