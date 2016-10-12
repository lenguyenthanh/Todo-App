package com.lenguyenthanh.todoapp.data

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.sqlbrite.BriteDatabase

interface DataDependencies{
    fun sharedPreferences(): SharedPreferences
    fun briteDatabase(): BriteDatabase
    fun moshi(): Moshi
}