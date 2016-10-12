package com.lenguyenthanh.todoapp.common.extentions

import android.content.SharedPreferences

inline fun SharedPreferences.quickEdit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.isLoaded(): Boolean =
        getBoolean("is.Loaded", false)

fun SharedPreferences.setLoaded(isLoaded: Boolean) = this.quickEdit {
    putBoolean("is.Loaded", isLoaded)
}