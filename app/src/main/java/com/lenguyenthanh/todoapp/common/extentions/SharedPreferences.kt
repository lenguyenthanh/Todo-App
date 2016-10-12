package com.lenguyenthanh.todoapp.common.extentions

import android.content.SharedPreferences

inline fun SharedPreferences.quickEdit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}