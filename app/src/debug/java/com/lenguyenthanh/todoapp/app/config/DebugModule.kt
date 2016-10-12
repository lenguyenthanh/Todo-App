package com.lenguyenthanh.todoapp.app.config

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.SharedPreferences
import com.lenguyenthanh.todoapp.common.dagger.OkHttpInterceptors
import com.lenguyenthanh.todoapp.common.dagger.OkHttpNetworkInterceptors
import com.lenguyenthanh.todoapp.config.Configuration
import com.lenguyenthanh.todoapp.config.Initializer
import com.lenguyenthanh.todoapp.tools.DebugActivityLifecycleCallbacks
import com.lenguyenthanh.todoapp.tools.DeveloperSettingModel
import com.lenguyenthanh.todoapp.tools.MemoryLeakProxy
import com.lenguyenthanh.todoapp.tools.MemoryLeakProxyImp
import dagger.Module
import dagger.Provides
import io.palaima.debugdrawer.timber.data.LumberYard
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class DebugModule {

    @Provides @Singleton
    internal fun provideLeakCanaryDebug(application: Application): MemoryLeakProxy = MemoryLeakProxyImp(application)

    @Provides @Singleton
    internal fun provideTimberDebug(application: Application): List<@JvmWildcard Timber.Tree> {
        val lumberYard = LumberYard.getInstance(application)
        lumberYard.cleanUp()
        return listOf(Timber.DebugTree(), lumberYard.tree())
    }

    @Provides @Singleton
    internal fun provideInitializerDebug(application: Application, forest: List<Timber.Tree>,
                                         lifecycleCallbacks: ActivityLifecycleCallbacks,
                                         httpLoggingInterceptor: HttpLoggingInterceptor): Initializer =
            DebugInitializer(application, forest, lifecycleCallbacks)

    @Provides @Singleton
    internal fun provideHttpLoggingInterceptorDebug(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.v(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides @OkHttpInterceptors @Singleton
    internal fun provideOkHttpInterceptorsDebug(
            httpLoggingInterceptor: HttpLoggingInterceptor): List<@JvmWildcard Interceptor> = listOf(httpLoggingInterceptor)

    @Provides @OkHttpNetworkInterceptors @Singleton
    internal fun provideOkHttpNetworkInterceptorsDebug(): List<@JvmWildcard Interceptor> = emptyList()

    @Provides @Singleton
    internal fun provideConfigurationDebug(): Configuration = DebugConfiguration()

    @Provides @Singleton
    internal fun provideDeveloperSettingModelDebug(application: Application, sharedPreferences: SharedPreferences,
                                                   memoryLeakProxy: MemoryLeakProxy) =
            DeveloperSettingModel(application, sharedPreferences, memoryLeakProxy)

    @Provides @Singleton
    internal fun provideDebugActivityLifecycleCallbacks(okHttpClient: OkHttpClient,
                                                        developerSettingModel: DeveloperSettingModel,
                                                        memoryLeakProxy: MemoryLeakProxy,
                                                        httpLoggingInterceptor: HttpLoggingInterceptor): ActivityLifecycleCallbacks =
            DebugActivityLifecycleCallbacks(okHttpClient, developerSettingModel, memoryLeakProxy, httpLoggingInterceptor)
}