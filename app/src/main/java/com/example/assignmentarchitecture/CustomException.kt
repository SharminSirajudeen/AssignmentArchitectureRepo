package com.example.assignmentarchitecture

import com.example.assignmentarchitecture.data.remote.NotOkException

class CustomException : Exception() {

    var exception: Throwable? = null
    var requestCode: Int = 0
    var position: Int = 0
    var tag: String? = null
    var notOkException: NotOkException? = null


}
