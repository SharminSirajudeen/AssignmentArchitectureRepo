package com.example.assignmentarchitecture.data.remote

import com.google.gson.Gson
import retrofit2.Response

class NotOkException private constructor(private var mCode: Int, private var mIssue: IssueResponse?) : Exception() {

    fun getmCode(): Int {
        return mCode
    }

    fun setmCode(mCode: Int) {
        this.mCode = mCode
    }

    fun getmIssue(): IssueResponse? {
        return mIssue
    }

    fun setmIssue(mIssue: IssueResponse) {
        this.mIssue = mIssue
    }

    companion object {

        fun newFromResponse(resp: Response<*>?): NotOkException? {
            var toRet: NotOkException? = null
            val iResponse: IssueResponse
            if (resp != null && !resp.isSuccessful) {
                val rawRespBody = resp.errorBody()
                if (rawRespBody != null) {
                    try {
                        iResponse = Gson().fromJson(rawRespBody.string(), IssueResponse::class.java)
                        toRet = NotOkException(resp.code(), iResponse)
                    } catch (ignored: Exception) {
                        //throw new IllegalArgumentException("Can't extract error from the response !");

                    }

                }
            } else {
                throw IllegalArgumentException("Can't extract error from the response !")
            }
            return toRet
        }
    }
}