package com.example.assignmentarchitecture.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import com.google.gson.Gson


/**
 * Generic Api  calling class
 *
 * @param <Req></Req>,Res> --> can be any response type object required
 */
abstract class GenericResponseHandler<Req, Res> {
    private val mResponse: MutableLiveData<Res>
    private val mReportedFailure: MutableLiveData<Throwable>
    private val params: Req? = null

    val isSuccessful: LiveData<Res>
        get() = mResponse

    val isFailed: LiveData<Throwable>
        get() = mReportedFailure

    init {
        this.mResponse = MutableLiveData()
        this.mReportedFailure = MutableLiveData()
    }

    fun callApi(params: Req?) {
        Log.e("Current Req is: ", Gson().toJson(params))
        proceeedApiCall(mResponse, mReportedFailure, params)
    }

    abstract fun proceeedApiCall(
        successResponseLiveData: MutableLiveData<Res>,
        failureResponseLiveData: MutableLiveData<Throwable>, params: Req?
    )
}