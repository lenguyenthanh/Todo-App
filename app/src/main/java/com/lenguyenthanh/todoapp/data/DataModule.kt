package com.lenguyenthanh.todoapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule() {

    @Provides @Singleton
    internal fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("net.localift.localift.data", Context.MODE_PRIVATE)
    }

    @Provides @Singleton
    internal fun provideGson(): Gson {
        return Gson()
    }
}
