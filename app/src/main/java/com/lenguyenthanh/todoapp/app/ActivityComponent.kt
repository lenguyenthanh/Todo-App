package com.lenguyenthanh.todoapp.app

import android.app.Activity
import com.lenguyenthanh.todoapp.common.dagger.ActivityScope
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(dependencies = arrayOf(TodoApp.AppComponent::class), modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent : AppDependencies {
    fun activity(): Activity
    fun inject(activity: MainActivity)
}

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides @ActivityScope
    fun provideActivity(): Activity = this.activity
}

