package com.lenguyenthanh.todoapp.common.extentions

import android.database.Cursor
import com.squareup.sqldelight.RowMapper

inline fun <T> Cursor?.mapTo(transform: RowMapper<T>): List<T> {
    val result = mutableListOf<T>()
    return if (this == null) result.toList() else {
        if (moveToFirst())
            do {
                result.add(transform.map(this))
            } while (moveToNext())
        result.toList()
    }
}


