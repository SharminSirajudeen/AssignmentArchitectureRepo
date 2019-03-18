package com.example.assignmentarchitecture

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

abstract class CustomApiResponseHanlder<Req, Res> {

    var customException = CustomException()
    private val mResponse: MutableLiveData<Res>
    private val mReportedFailure: MutableLiveData<CustomException>

    val isSuccessful: LiveData<Res>
        get() = mResponse

    val isFailed: LiveData<CustomException>
        get() = mReportedFailure

    init {
        this.mResponse = MutableLiveData()
        this.mReportedFailure = MutableLiveData()
    }

    fun callApi(params: Req, requestCode: Int, position: Int, tag: String) {
        proceeedApiCall(mResponse, mReportedFailure, params, requestCode, position, tag)
    }

    abstract fun proceeedApiCall(
        successResponseLiveData: MutableLiveData<Res>,
        failureResponseLiveData: MutableLiveData<CustomException>,
        params: Req,
        requestCode: Int,
        position: Int,
        tag: String
    )


}
