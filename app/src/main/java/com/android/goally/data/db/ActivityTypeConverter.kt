package com.android.goally.data.db

import androidx.room.TypeConverter
import com.android.goally.data.model.api.response.copilot.Activity

class ActivityTypeConverter {
    @TypeConverter
    fun fromActivityList(activities: List<Activity>?): String? {
        return activities?.joinToString(separator = ";") { it.name + "," + it.imgURL }
    }

    @TypeConverter
    fun toActivityList(data: String?): List<Activity>? {
        return data?.split(";")?.map {
            val parts = it.split(",")
            Activity(name = parts[0], imgURL = parts[1])
        }
    }
}
