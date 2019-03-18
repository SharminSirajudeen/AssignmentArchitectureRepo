package com.example.assignmentarchitecture.data.remote

import android.support.annotation.IntDef

import com.google.gson.annotations.SerializedName

class IssueResponse {

    @SerializedName("error")
    @IssueType
    var mError: Int = 0

    @SerializedName("message")
    var mMessage: String? = null

    @IntDef(IssueType.ALERT, IssueType.FAIL)
    annotation class IssueType {
        companion object {
            const val ALERT = 1
            const val FAIL = 3
        }
    }


}