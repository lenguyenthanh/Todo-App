package com.lenguyenthanh.todoapp.app

import android.app.Application
import android.content.res.Resources
import com.lenguyenthanh.todoapp.config.Configuration
import com.lenguyenthanh.todoapp.data.DataDependencies
import com.lenguyenthanh.todoapp.data.network.ServiceDependencies
import com.lenguyenthanh.todoapp.data.TasksLoader
import com.lenguyenthanh.todoapp.ui.UIDependencies

interface AppDependencies : DataDependencies, UIDependencies, ServiceDependencies {
    fun configuration(): Configuration
    fun resources(): Resources
    fun application(): Application
    fun tasksLoader(): TasksLoader
}