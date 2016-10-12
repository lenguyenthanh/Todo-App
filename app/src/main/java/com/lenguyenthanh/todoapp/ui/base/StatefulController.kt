package com.lenguyenthanh.todoapp.ui.base

import android.os.Bundle
import android.view.View
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.extentions.snackbarShort
import com.lenguyenthanh.todoapp.ui.widget.BetterViewAnimator


abstract class StatefulController<in Data> : BaseController {

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    private lateinit var viewAnimator: BetterViewAnimator

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewAnimator = view.findViewById(R.id.contentLayout) as BetterViewAnimator
    }

    fun showLoadingView() =
            viewAnimator.setDisplayedChildId(R.id.loadingView)

    fun showError(message: String, isLightError: Boolean) {
        if (!isLightError) {
            viewAnimator.setDisplayedChildId(R.id.errorView)
            showErrorMessage(message)
        } else {
            showLightError(message)
        }
    }

    protected fun showEmpty() =
            viewAnimator.setDisplayedChildId(R.id.emptyView)

    protected fun showLightError(message: String) =
            snackbarShort(viewAnimator, message)

    open fun showErrorMessage(message: String) {
    }

    open fun setData(data: Data) {
        if (isEmpty(data)) {
            showEmpty()
        } else {
            viewAnimator.setDisplayedChildId(R.id.contentView)
        }
    }

    open fun isEmpty(data: Data) = false
}

