package com.android.goally.data.db

import androidx.room.TypeConverter
import com.android.goally.data.model.api.response.copilot.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScheduleTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromSchedule(schedule: Schedule?): String? {
        return schedule?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toSchedule(data: String?): Schedule? {
        return data?.let { gson.fromJson(it, Schedule::class.java) }
    }
}
