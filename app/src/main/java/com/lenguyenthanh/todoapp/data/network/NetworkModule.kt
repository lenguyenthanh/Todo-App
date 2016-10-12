package com.lenguyenthanh.todoapp.data.network

import android.app.Application
import com.lenguyenthanh.todoapp.common.dagger.OkHttpInterceptors
import com.lenguyenthanh.todoapp.common.dagger.OkHttpNetworkInterceptors
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class NetworkModule {

    private val DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50MB
    private val DISK_CACHE_FOLDER = "http"

    @Provides @Singleton
    internal fun provideOkHttpClient(app: Application,
                                     @OkHttpInterceptors interceptors: List<Interceptor>,
                                     @OkHttpNetworkInterceptors networkInterceptors: List<Interceptor>): OkHttpClient {
        return createOkHttpClient(createOkHttpClientBuilder(app), interceptors, networkInterceptors)
    }

    private fun createOkHttpClient(builder: OkHttpClient.Builder,
                                   interceptors: List<Interceptor>,
                                   networkInterceptors: List<Interceptor>): OkHttpClient {
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }

        for (networkInterceptor in networkInterceptors) {
            builder.addNetworkInterceptor(networkInterceptor)
        }
        return builder.build()
    }

    private fun createOkHttpClientBuilder(app: Application) =
            OkHttpClient.Builder().cache(createHttpCache(app))

    private fun createHttpCache(application: Application): Cache {
        val cacheDir = File(application.cacheDir, DISK_CACHE_FOLDER)
        return Cache(cacheDir, DISK_CACHE_SIZE.toLong())
    }
}