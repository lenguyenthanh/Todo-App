package com.lenguyenthanh.todoapp.tools

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

interface MemoryLeakProxy {
    fun init()
    fun watch(any: Any)
}

class MemoryLeakProxyImp(private val application: Application) : MemoryLeakProxy {

    private var refWatcher: RefWatcher? = null

    override fun init() {
        refWatcher = LeakCanary.install(application)
    }

    override fun watch(any: Any) {
        refWatcher?.watch(any)
    }
}