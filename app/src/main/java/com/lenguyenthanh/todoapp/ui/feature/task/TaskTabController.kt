package com.lenguyenthanh.todoapp.ui.feature.task

import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.EditText
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.extentions.stringResource
import com.lenguyenthanh.todoapp.data.TasksLoader
import com.lenguyenthanh.todoapp.ui.base.BaseController
import kotlinx.android.synthetic.main.screen_task_tab.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class TaskTabController : BaseController() {

    init {
        setHasOptionsMenu(true)
    }

    @Inject
    internal lateinit var tasksLoader: TasksLoader
    private var isPendingTab = false

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
        mainActivity().setSupportActionBar(view.toolbar)
        view.view_pager.adapter = pagerAdapter
        view.tab_layout.setupWithViewPager(view.view_pager)
        view.toolbar.title = stringResource(R.string.app_name)
        view.tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                isPendingTab = tab.position == 1
                activity.invalidateOptionsMenu()
            }

        })
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> showEnterTaskNameDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val addItem = menu.findItem(R.id.action_add)
        addItem.isVisible = isPendingTab
    }

    private fun showEnterTaskNameDialog(){
        buildEnterTaskNameDialog {
            tasksLoader.createTask(it)
                .subscribe()
        }.show()
    }

    private fun buildEnterTaskNameDialog(func: (String) -> Unit): AlertDialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_task_name, null)
        val etTaskName = view.findViewById(R.id.etTaskName) as EditText
        return AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(android.R.string.ok, { t1, t2 -> func(etTaskName.text.toString()) })
                .setNegativeButton(android.R.string.cancel, { t1, t2 -> })
                .create()
    }

}