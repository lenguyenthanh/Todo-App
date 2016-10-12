package com.lenguyenthanh.todoapp.config

import android.app.Application
import timber.log.Timber

open class MainInitializer(protected val application: Application, protected val logTrees: List<Timber.Tree>) : Initializer {

    override fun initialize() {
        initializeLog()
    }


    private fun initializeLog() {
        logTrees.forEach { Timber.plant(it) }
    }
}