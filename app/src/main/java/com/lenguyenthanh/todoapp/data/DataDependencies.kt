package com.lenguyenthanh.todoapp.data

import android.content.SharedPreferences
import com.google.gson.Gson

interface DataDependencies {
    fun sharedPreferences(): SharedPreferences
    fun gson(): Gson
}