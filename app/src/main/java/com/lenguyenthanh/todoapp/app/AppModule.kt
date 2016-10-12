package com.lenguyenthanh.todoapp.app

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides @Singleton fun providesApplication(): Application {
        return application
    }

    @Provides @Singleton fun providesResource(): Resources {
        return application.resources
    }
}