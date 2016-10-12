package com.lenguyenthanh.todoapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.lenguyenthanh.todoapp.app.MainActivity
import rx.subscriptions.CompositeSubscription

abstract class BaseController : Controller {

    protected constructor() : super()

    protected constructor(args: Bundle?) : super(args)

    fun mainActivity() = this.activity as MainActivity

    protected lateinit var rxSubscription: CompositeSubscription
    override fun onAttach(view: View) {
        super.onAttach(view)
        rxSubscription = CompositeSubscription()
    }

    override fun onDetach(view: View) {
        rxSubscription.unsubscribe()
        super.onDetach(view)
    }


    abstract protected fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        preCreateView()
        val view = inflateView(inflater, container)
        onViewBound(view)
        return view
    }


    open protected fun onViewBound(view: View) {
    }

    open protected fun preCreateView() {

    }

}