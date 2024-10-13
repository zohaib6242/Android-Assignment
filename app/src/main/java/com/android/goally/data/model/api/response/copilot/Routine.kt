package com.android.goally.data.model.api.response.copilot

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.goally.data.db.ActivityTypeConverter
import java.io.Serializable
@Entity(tableName = "routine_table")
data class Routine(
    @PrimaryKey val _id: String,
    val name: String,
    val imgURL: String,
    val folder: String,
    @TypeConverters(ActivityTypeConverter::class) val activities: List<Activity>? = null,
    val scheduleV2: Schedule? = null
)


