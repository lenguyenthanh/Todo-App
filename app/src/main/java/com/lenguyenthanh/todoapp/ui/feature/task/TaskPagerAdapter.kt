package com.lenguyenthanh.todoapp.ui.feature.task

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.support.ControllerPagerAdapter
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.extentions.stringResource

class TaskPagerAdapter(val host: Controller) : ControllerPagerAdapter(host, false) {

    private var controllers: List<Controller> = emptyList()
    override fun getItem(position: Int): Controller? {
        return controllers[position]
    }

    override fun getCount(): Int {
        return controllers.size
    }

    fun setControllers(controllers: List<Controller>) {
        this.controllers = controllers
        notifyDataSetChanged()
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> host.stringResource(R.string.done)
            1 -> host.stringResource(R.string.pending)
            else -> ""
        }
    }
}