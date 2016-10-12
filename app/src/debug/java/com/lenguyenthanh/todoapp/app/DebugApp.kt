package com.lenguyenthanh.todoapp.app

import com.lenguyenthanh.todoapp.app.AppModule
import com.lenguyenthanh.todoapp.app.TodoApp
import com.lenguyenthanh.todoapp.data.DataModule
import com.lenguyenthanh.todoapp.data.network.NetworkModule
import com.lenguyenthanh.todoapp.ui.UIModule
import dagger.Component
import com.lenguyenthanh.todoapp.app.config.DebugModule
import javax.inject.Singleton

class DebugApp : TodoApp() {

    @Singleton
    @Component(modules = arrayOf(AppModule::class, NetworkModule::class,
            UIModule::class, DataModule::class, DebugModule::class))
    interface AppComponent : TodoApp.AppComponent {
    }

    override fun initializeDaggerComponent() {
        appComponent = DaggerDebugApp_AppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)
    }
}