package com.example.assignmentarchitecture.di.modules

import android.util.Log
import com.example.assignmentarchitecture.App
import com.example.assignmentarchitecture.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    private val CONNECTION_TIMEOUT: Int = 30000

    @Provides
    @Singleton
    fun provideOkHttpClient(app: App): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message -> Log.d("APIRetro", message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
            builder.addInterceptor(ChuckInterceptor(app.applicationContext).showNotification(true))
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}