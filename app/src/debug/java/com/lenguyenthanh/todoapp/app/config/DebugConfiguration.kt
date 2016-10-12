package com.lenguyenthanh.todoapp.app.config

import com.lenguyenthanh.todoapp.config.Configuration

class DebugConfiguration : Configuration {
    override fun getBaseApiUrl() = "https://dl.dropboxusercontent.com/u/6890301/"
}