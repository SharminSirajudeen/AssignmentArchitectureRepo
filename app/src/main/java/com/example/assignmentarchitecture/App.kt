package com.example.assignmentarchitecture

import com.example.assignmentarchitecture.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): App {
            return instance as App
        }
    }


    override fun onCreate() {
        super.onCreate()
    }


    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().create(this)
    }

}

