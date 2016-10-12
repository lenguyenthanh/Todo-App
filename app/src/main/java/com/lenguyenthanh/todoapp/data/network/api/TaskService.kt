package com.lenguyenthanh.todoapp.data.network.api

import com.lenguyenthanh.todoapp.data.network.model.TasksResponse
import retrofit2.http.GET
import rx.Observable

interface TaskService {
    @GET("tasks.json") fun items(): Observable<TasksResponse>
}