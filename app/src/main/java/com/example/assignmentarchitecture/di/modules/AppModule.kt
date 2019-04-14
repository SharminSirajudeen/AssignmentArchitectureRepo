package com.example.assignmentarchitecture.di.modules

import android.content.Context
import com.example.assignmentarchitecture.App
import com.example.assignmentarchitecture.framework.AppDataManager
import com.example.assignmentarchitecture.framework.MarkerDataManager
import com.example.assignmentarchitecture.data.local.PreferencesImpl
import com.example.assignmentarchitecture.data.local.IPreferences
import com.example.assignmentarchitecture.data.remote.Api
import com.example.assignmentarchitecture.di.PreferenceInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApi(retorfit: Retrofit): Api {
        return retorfit.create(Api::class.java)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Provides
    @Singleton
    fun application(app: App): Context = app


    @Provides
    @Singleton
    internal fun provideIPreferences(preferencesImpl: PreferencesImpl): IPreferences {
        return preferencesImpl
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): MarkerDataManager {
        return appDataManager
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return "AssignmentPreferences"
    }

}