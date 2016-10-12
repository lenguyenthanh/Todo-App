package com.lenguyenthanh.todoapp.data.network.model

import com.squareup.moshi.Json

data class TaskEntity(
        @Json(name = "id") val id: Long,
        @Json(name = "name") val name: String,
        @Json(name = "state") val state: Int
) {
    fun isDone() = state == 1
}