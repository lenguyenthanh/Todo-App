package com.lenguyenthanh.todoapp.config

import android.app.Application
import timber.log.Timber

class ReleaseInitializer(application: Application, forest: List<Timber.Tree>) : MainInitializer(application, forest) {
    override fun initialize() {
        super.initialize()
        initializeCrashlytics()
    }

    private fun initializeCrashlytics() {
//        Fabric.with(application, Crashlytics())
    }
}

