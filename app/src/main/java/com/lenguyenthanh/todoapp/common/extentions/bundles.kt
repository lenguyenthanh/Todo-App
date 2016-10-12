package com.lenguyenthanh.todoapp.common.extentions

import android.os.Bundle

inline fun bundle(body: Bundle.() -> Unit): Bundle {
    val bundle = Bundle()
    bundle.body()
    return bundle
}