package com.lenguyenthanh.todoapp.ui.feature.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.extentions.bundle
import com.lenguyenthanh.todoapp.common.extentions.verticalLayoutManager
import com.lenguyenthanh.todoapp.data.TasksLoader
import com.lenguyenthanh.todoapp.model.Task
import com.lenguyenthanh.todoapp.ui.base.StatefulController
import com.lenguyenthanh.todoapp.ui.exception.ErrorMessageFactory
import com.lenguyenthanh.todoapp.ui.widget.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.screen_tasks.view.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

open class TasksController : StatefulController<List<Task>> {

    @Inject
    internal lateinit var tasksLoader: TasksLoader
    @Inject
    internal lateinit var errorMessageFactory: ErrorMessageFactory

    companion object {
        val KEY_IS_DONE = "TasksController.isDone"

        fun getDoneInstance(): TasksController {
            return TasksController()
        }

        fun getPendingInstance(): TasksController {
            val bundle = bundle() {
                putBoolean(KEY_IS_DONE, false)
            }
            return TasksController(bundle)
        }
    }

    private var isDone = true

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle) {
        bundle?.let {
            isDone = it.getBoolean(KEY_IS_DONE)
        }
    }

    private val tasksAdapter by lazy {
        TasksAdapter(activity.layoutInflater) {
            val exe = if (!it.isDone()) {
                tasksLoader.markTaskAsDone(it.id, it.name)
            } else {
                tasksLoader.markTaskAsPending(it.id, it.name)
            }
            exe.subscribe({Timber.d("Index $it")},{Timber.e(it, "Update task state error")})
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.screen_tasks, container, false)
    }

    override fun preCreateView() {
        super.preCreateView()
        mainActivity().activityComponent.inject(this)
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        initializeRecyclerView(view)
    }

    private fun getDataSource(): Observable<List<Task>> {
        return if (isDone) {
            tasksLoader.fetchDoneTasks()
        } else {
            tasksLoader.fetchPendingTask()
        }
    }

    private fun bindData() {
        rxSubscription.add(getDataSource()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d(it.toString())
                    setData(it)
                }, {
                    showError(errorMessageFactory.create(it), isLightError = true)
                })
        )
    }

    private fun initializeRecyclerView(view: View) {
        with(view.contentView) {
            setHasFixedSize(true)
            layoutManager = verticalLayoutManager()
            addItemDecoration(SimpleDividerItemDecoration(activity))
            adapter = tasksAdapter
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        bindData()
    }

    override fun setData(data: List<Task>) {
        super.setData(data)
        tasksAdapter.replaceData(data)
    }

    override fun isEmpty(data: List<Task>): Boolean {
        return data.isEmpty()
    }
}