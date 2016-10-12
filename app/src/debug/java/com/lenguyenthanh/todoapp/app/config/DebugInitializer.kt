package com.lenguyenthanh.todoapp.app.config

import android.app.Application
import com.facebook.stetho.Stetho
import com.lenguyenthanh.todoapp.config.MainInitializer
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugInitializer @Inject constructor(application: Application, forest: List<Timber.Tree>,
                                           private val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks) :
        MainInitializer(application, forest) {

    override fun initialize() {
        super.initialize()
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        Stetho.initializeWithDefaults(application)
    }
}