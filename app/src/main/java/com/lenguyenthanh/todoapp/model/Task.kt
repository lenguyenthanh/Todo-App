package com.lenguyenthanh.todoapp.model

import com.lenguyenthanh.todoapp.data.cache.model.TaskDb

data class Task(
        val id: Long,
        val name: String,
        val state: Int
)

fun TaskDb.toTask(): Task {
    val state = if(state()) 1 else 0
    return Task(id(), name(), state)
}