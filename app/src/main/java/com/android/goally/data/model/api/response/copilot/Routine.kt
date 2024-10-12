package com.android.goally.data.model.api.response.copilot

import java.io.Serializable

data class Routine(
    val _id: String,
    val name: String,
    val imgURL: String,
    val folder: String,
    val activities: List<Activity>?,
    val scheduleV2: Schedule?
)

