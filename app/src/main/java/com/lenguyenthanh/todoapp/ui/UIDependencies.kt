package com.lenguyenthanh.todoapp.ui

import com.lenguyenthanh.todoapp.ui.exception.ErrorMessageFactory

interface UIDependencies {
    fun errorMessageFactory(): ErrorMessageFactory
}