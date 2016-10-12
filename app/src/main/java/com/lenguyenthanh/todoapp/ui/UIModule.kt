package com.lenguyenthanh.todoapp.ui

import android.app.Application
import com.lenguyenthanh.todoapp.ui.exception.ErrorMessageFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UIModule {

    @Provides @Singleton
    fun provideErrorMessageFactory(application: Application): ErrorMessageFactory = ErrorMessageFactory(application)


}