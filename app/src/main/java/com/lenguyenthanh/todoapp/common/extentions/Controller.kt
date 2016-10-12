package com.lenguyenthanh.todoapp.common.extentions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router

fun Controller.stringResource(resourceId: Int): String {
    return this.resources.getString(resourceId)
}

fun Controller.getChildRouter(container: Int, tag: String? = null): Router? {
    view?.let {
        return getChildRouter(view.findViewById(container) as ViewGroup, tag)
    }
    return null
}

fun Controller.isNoChildIn(container: Int, tag: String? = null): Boolean
        = getChildRouter(container, tag)?.backstack?.size == 0



fun Context.copyToClipBoard(text: String, label: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.primaryClip = clip
}



fun Controller.snackbarShort(view: View, messageId: Int) {
    snackbarShort(view, stringResource(messageId))
}

fun snackbarShort(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Controller.linearLayoutManager(direction: Int): LinearLayoutManager {
    val linearLayoutManage = LinearLayoutManager(activity)
    linearLayoutManage.orientation = direction
    return linearLayoutManage
}

fun Controller.horizontalLayoutManager() =
        linearLayoutManager(LinearLayoutManager.HORIZONTAL)

fun Controller.verticalLayoutManager() =
        linearLayoutManager(LinearLayoutManager.VERTICAL)

