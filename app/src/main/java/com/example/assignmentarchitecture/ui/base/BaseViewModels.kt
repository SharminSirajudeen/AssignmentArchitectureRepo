package com.example.assignmentarchitecture.ui.base

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.example.assignmentarchitecture.data.remote.Api

import java.lang.ref.WeakReference

abstract class BaseViewModels<N>(val dataManager: Api) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    private var mNavigator: WeakReference<N>? = null

    var navigator: N
        get() = mNavigator!!.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    abstract fun retryAPICalling()

    override fun onCleared() {
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }


    abstract fun loadData()
}
