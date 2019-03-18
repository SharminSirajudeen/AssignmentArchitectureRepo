package com.example.assignmentarchitecture.di.components

import com.example.assignmentarchitecture.App
import com.example.assignmentarchitecture.di.modules.ActivityBuildersModule
import com.example.assignmentarchitecture.di.modules.AppModule
import com.example.assignmentarchitecture.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class, NetworkModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}

