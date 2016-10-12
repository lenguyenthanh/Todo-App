package com.lenguyenthanh.todoapp.tools

import android.app.Application
import android.content.SharedPreferences
import com.lenguyenthanh.todoapp.common.extentions.quickEdit
import java.util.concurrent.atomic.AtomicBoolean

class DeveloperSettingModel constructor(private val application: Application, private val preferences: SharedPreferences,
                                        private val memoryLeakProxy: MemoryLeakProxy) {

    private val PREF_STETHO = "pref.stetho"
    private val PREF_LEAKCANARY = "pref.leakCanary"

    private val stethoAlreadyEnabled = AtomicBoolean()

    private val leakCanaryAlreadyEnabled = AtomicBoolean()

    fun apply() {
        // Stetho can not be enabled twice.
        if (stethoAlreadyEnabled.compareAndSet(false, true)) {
            if (isStethoEnabled()) {
//                Stetho.initializeWithDefaults(application)
            }
        }

        // LeakCanary can not be enabled twice.
        if (leakCanaryAlreadyEnabled.compareAndSet(false, true)) {
            if (isLeakCanaryEnabled()) {
                memoryLeakProxy.init()
            }
        }
    }

    fun changeStethoState(enabled: Boolean) {
        saveIsStethoEnabled(enabled)
        apply()
    }

    fun changeLeakCanaryState(enabled: Boolean) {
        saveIsLeakCanaryEnabled(enabled)
        apply()
    }

    fun isStethoEnabled(): Boolean {
        return preferences.getBoolean(PREF_STETHO, false)
    }

    fun saveIsStethoEnabled(isStethoEnabled: Boolean) {
        preferences.quickEdit {
            putBoolean(PREF_STETHO, isStethoEnabled)
        }
    }

    fun isLeakCanaryEnabled(): Boolean {
        return preferences.getBoolean(PREF_LEAKCANARY, false)
    }

    fun saveIsLeakCanaryEnabled(isLeakCanaryEnabled: Boolean) {
        preferences.quickEdit {
            putBoolean(PREF_LEAKCANARY, isLeakCanaryEnabled)
        }
    }
}