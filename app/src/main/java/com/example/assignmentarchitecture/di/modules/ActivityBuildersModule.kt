package com.example.assignmentarchitecture.di.modules

import com.example.assignmentarchitecture.ui.activities.mainactivity.MainActivity
import com.example.assignmentarchitecture.ui.activities.mainactivity.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

}