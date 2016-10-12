package com.lenguyenthanh.todoapp.config

import android.app.Application
import com.lenguyenthanh.todoapp.common.dagger.OkHttpInterceptors
import com.lenguyenthanh.todoapp.common.dagger.OkHttpNetworkInterceptors
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class SupportModule {

    @Provides @Singleton
    internal fun provideTimbers(): List<@JvmWildcard Timber.Tree> = emptyList() // It should be Crashlytic tree or some other crash report tool tree

    @Provides @Singleton
    internal fun provideInitializer(application: Application, forest: List<Timber.Tree>): Initializer = ReleaseInitializer(application, forest)

    @Provides @Singleton
    internal fun provideConfiguration(): Configuration = ReleaseConfiguration()

    @Provides @Singleton @OkHttpInterceptors
    internal fun provideOkHttpInterceptors(): List<@JvmWildcard Interceptor> = emptyList()

    @Provides @Singleton @OkHttpNetworkInterceptors
    internal fun provideOkHttpNetworkInterceptors(): List<@JvmWildcard Interceptor> = emptyList()
}