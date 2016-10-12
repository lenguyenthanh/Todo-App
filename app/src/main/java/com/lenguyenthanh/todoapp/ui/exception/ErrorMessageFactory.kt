package com.lenguyenthanh.todoapp.ui.exception

import android.app.Application
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.ServerException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorMessageFactory constructor(private val application: Application) {

    fun create(exception: Throwable): String {
        var message = application.getString(R.string.generic_error_message)

        if (exception is UnknownHostException) {
            message = application.getString(R.string.error_network_connection)
        } else if (exception is SocketTimeoutException) {
            message = application.getString(R.string.error_time_out)
        } else if (exception is ServerException) {
            message = exception.message
        }

        return message
    }
}