package com.lenguyenthanh.todoapp.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator

class BetterViewAnimator(context: Context, attrs: AttributeSet) : ViewAnimator(context, attrs) {
    fun setDisplayedChildId(id: Int) {
        if (getDisplayedChildId() == id) {
            return
        }
        var i = 0
        val count = childCount
        while (i < count) {
            if (getChildAt(i).id == id) {
                displayedChild = i
                return
            }
            i++
        }
        throw IllegalArgumentException("No captureShopperCardView with ID " + id)
    }

    fun getDisplayedChildId(): Int {
        return getChildAt(displayedChild).id
    }
}