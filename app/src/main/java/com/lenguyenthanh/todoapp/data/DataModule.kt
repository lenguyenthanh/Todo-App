package com.lenguyenthanh.todoapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lenguyenthanh.todoapp.data.cache.TodoAppSqlHelper
import com.lenguyenthanh.todoapp.data.network.api.TaskService
import com.squareup.sqlbrite.BriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule() {

    @Provides @Singleton
    internal fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("com.lenguyenthanh.todoapp.data", Context.MODE_PRIVATE)
    }

    @Provides @Singleton
    internal fun provideBriteDatabase(application: Application): BriteDatabase {
        return TodoAppSqlHelper.getBriteDatabase(application)
    }

    @Provides @Singleton
    fun provideTasksLoader(briteDatabase: BriteDatabase, taskService: TaskService, sharedPreferences: SharedPreferences): TasksLoader {
        return TasksLoaderImpl(briteDatabase, taskService, sharedPreferences)
    }
}
