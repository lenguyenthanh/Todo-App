package com.lenguyenthanh.todoapp.data.cache

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lenguyenthanh.todoapp.data.cache.model.TaskDb
import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import rx.schedulers.Schedulers

class TodoAppSqlHelper : SQLiteOpenHelper {

    companion object {
        private val DATABASE_VERSION = 1
        private var instance: BriteDatabase? = null
        fun getBriteDatabase(context: Context): BriteDatabase {
            if (instance == null) {
                val sqlBrite = SqlBrite.create()
                instance = sqlBrite.wrapDatabaseHelper(TodoAppSqlHelper(context), Schedulers.io())
            }
            return instance!!
        }
    }

    constructor(context: Context) : super(context, null, null, DATABASE_VERSION) {

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TaskDb.CREATE_TABLE);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}

