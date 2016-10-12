package com.lenguyenthanh.todoapp.tools

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.jakewharton.processphoenix.ProcessPhoenix
import com.lenguyenthanh.todoapp.app.MainActivity
import io.palaima.debugdrawer.DebugDrawer
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.ButtonAction
import io.palaima.debugdrawer.actions.SpinnerAction
import io.palaima.debugdrawer.actions.SwitchAction
import io.palaima.debugdrawer.commons.BuildModule
import io.palaima.debugdrawer.commons.DeviceModule
import io.palaima.debugdrawer.commons.SettingsModule
import io.palaima.debugdrawer.fps.FpsModule
import io.palaima.debugdrawer.okhttp3.OkHttp3Module
import io.palaima.debugdrawer.scalpel.ScalpelModule
import io.palaima.debugdrawer.timber.TimberModule
import jp.wasabeef.takt.Takt
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugActivityLifecycleCallbacks(private val okHttpClient: OkHttpClient,
                                      private val developerSettingModel: DeveloperSettingModel,
                                      private val memoryLeakProxy: MemoryLeakProxy,
                                      private val httpLoggingInterceptor: HttpLoggingInterceptor) : Application.ActivityLifecycleCallbacks {

    private var debugDrawer: DebugDrawer? = null
    private var isFirstTime: Boolean = true
    override fun onActivityPaused(activity: Activity?) {
        debugDrawer?.onPause()
    }


    override fun onActivityStarted(activity: Activity?) {
        if (isFirstTime) {
            setupDebugDrawer(activity)
            isFirstTime = false
        }
        debugDrawer?.onStart()
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let {
            memoryLeakProxy.watch(it)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        debugDrawer?.onStop()
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        applyDebugTools()
    }

    override fun onActivityResumed(activity: Activity?) {
        debugDrawer?.onResume()
    }

    private fun applyDebugTools() {
        developerSettingModel.apply()
    }

    private fun setupDebugDrawer(activity: Activity?) {
        debugDrawer = activity?.let {
            DebugDrawer.Builder(it).modules(
                    DeviceModule(it),
                    BuildModule(it),
                    actionsModule(it),
                    OkHttp3Module(okHttpClient),
                    ScalpelModule(it),
                    TimberModule(),
                    SettingsModule(it),
                    FpsModule(Takt.stock(it.application)))
                    .build()
        }
    }

    private fun actionsModule(activity: Activity?): ActionsModule {
        val leakCanaryAction = SwitchAction("Enable Leak Canary", { developerSettingModel.changeLeakCanaryState(it) })
        val loggingAction = SpinnerAction(HttpLoggingInterceptor.Level.values().toList(), { httpLoggingInterceptor.level = it })

        val restartAction = ButtonAction("Restart App", { ProcessPhoenix.triggerRebirth(activity, Intent(activity, MainActivity::class.java)) })
        return ActionsModule(leakCanaryAction, loggingAction, restartAction)
    }

}
