package com.lenguyenthanh.todoapp.app

import android.app.Activity
import com.lenguyenthanh.todoapp.common.dagger.ActivityScope
import com.lenguyenthanh.todoapp.ui.feature.task.TaskTabController
import com.lenguyenthanh.todoapp.ui.feature.task.TasksController
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(dependencies = arrayOf(TodoApp.AppComponent::class), modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent : AppDependencies {
    fun activity(): Activity
    fun inject(activity: MainActivity)
    fun inject(tasksController: TasksController)
    fun inject(taskTabController: TaskTabController)
}

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides @ActivityScope
    fun provideActivity(): Activity = this.activity
}

