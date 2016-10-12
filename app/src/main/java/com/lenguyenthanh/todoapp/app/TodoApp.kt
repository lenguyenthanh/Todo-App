package com.lenguyenthanh.todoapp.app

import android.app.Application
import android.content.Context
import com.lenguyenthanh.todoapp.config.Initializer
import com.lenguyenthanh.todoapp.config.SupportModule
import com.lenguyenthanh.todoapp.data.DataModule
import com.lenguyenthanh.todoapp.data.network.NetworkModule
import com.lenguyenthanh.todoapp.ui.UIModule
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

open class TodoApp : Application() {
    @Inject
    internal lateinit var initializer: Initializer

    protected lateinit var appComponent: AppComponent

    fun getComponent(): AppComponent = appComponent


    companion object {
        operator fun get(context: Context): TodoApp {
            return context.applicationContext as TodoApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeDaggerComponent()
        initializer.initialize()
    }

    open protected fun initializeDaggerComponent() {
        appComponent = DaggerTodoApp_AppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }

    @Singleton
    @Component(modules = arrayOf(AppModule::class, NetworkModule::class, DataModule::class, SupportModule::class, UIModule::class))
    interface AppComponent : AppDependencies {
        fun inject(app: TodoApp)
    }
}