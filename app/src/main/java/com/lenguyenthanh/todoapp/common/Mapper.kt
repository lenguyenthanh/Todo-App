package com.lenguyenthanh.todoapp.common

interface Mapper<T1, T2> {
    fun map(t: T1): T2
    fun reverseMap(t: T2): T1
    fun map(list: List<T1>): List<T2> = list.map { this.map(it) }
    fun reverseMap(list: List<T2>): List<T1> = list.map { this.reverseMap(it) }
}
