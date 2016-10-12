package com.lenguyenthanh.todoapp.ui.feature.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.data.TasksLoader
import com.lenguyenthanh.todoapp.ui.base.BaseController
import kotlinx.android.synthetic.main.screen_task_tab.view.*
import javax.inject.Inject

class TaskTabController : BaseController() {

    @Inject
    internal lateinit var tasksLoader: TasksLoader

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.screen_task_tab, container, false)
    }

    private val pagerAdapter by lazy {
        TaskPagerAdapter(this)
    }

    override fun preCreateView() {
        super.preCreateView()
        mainActivity().activityComponent.inject(this)
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.view_pager.adapter = pagerAdapter
        view.tab_layout.setupWithViewPager(view.view_pager)
    }


    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        if (changeType == ControllerChangeType.PUSH_ENTER || changeType == ControllerChangeType.POP_ENTER) {
            if (pagerAdapter.count == 0) {
                pagerAdapter.setControllers(controllers = listOf(TasksController.getDoneInstance(), TasksController.getPendingInstance()))
            }
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tasksLoader.fetchData()
    }
}