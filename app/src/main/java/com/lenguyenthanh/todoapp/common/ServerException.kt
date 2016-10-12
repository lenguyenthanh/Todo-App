package com.lenguyenthanh.todoapp.common

class ServerException(message: String, val code: Int) : RuntimeException(message) {

}