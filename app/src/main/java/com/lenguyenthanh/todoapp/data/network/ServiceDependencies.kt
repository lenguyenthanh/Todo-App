package com.lenguyenthanh.todoapp.data.network

import com.lenguyenthanh.todoapp.data.network.api.TaskService

interface ServiceDependencies {
    fun taskService(): TaskService
}