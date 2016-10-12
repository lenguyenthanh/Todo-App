package com.lenguyenthanh.todoapp.data

import android.content.SharedPreferences
import com.lenguyenthanh.todoapp.common.extentions.isLoaded
import com.lenguyenthanh.todoapp.common.extentions.mapTo
import com.lenguyenthanh.todoapp.common.extentions.setLoaded
import com.lenguyenthanh.todoapp.data.cache.model.TaskDb
import com.lenguyenthanh.todoapp.data.network.api.TaskService
import com.lenguyenthanh.todoapp.data.network.model.TaskEntity
import com.lenguyenthanh.todoapp.model.Task
import com.lenguyenthanh.todoapp.model.toTask
import com.squareup.sqlbrite.BriteDatabase
import rx.Observable
import timber.log.Timber

interface TasksLoader {
    fun fetchData()
    fun fetchDoneTasks(): Observable<List<Task>>
    fun fetchPendingTask(): Observable<List<Task>>
    fun markTaskAsDone(id: Int): Observable<Boolean>
    fun markTaskAsPending(id: Int): Observable<Boolean>
    fun deleteATask(id: Int): Observable<Boolean>
    fun undoATask(id: Int): Observable<Boolean>
}

class TasksLoaderImpl(private val briteDatabase: BriteDatabase, private val taskService: TaskService, private val sharedPreferences: SharedPreferences) : TasksLoader {

    override fun fetchData() {
        if (sharedPreferences.isLoaded()) {
            fetchTasksFromApi()
        }
    }

    override fun fetchDoneTasks(): Observable<List<Task>> {
        return briteDatabase.createQuery(TaskDb.TABLE_NAME, TaskDb.SELECT_DONE_TAKS)
                .map { it.run() }
                .map { it.mapTo(TaskDb.SELECT_DONE_TASK_MAPPER) }
                .map { it.map { it.toTask() } }
    }

    override fun fetchPendingTask(): Observable<List<Task>> {
        return briteDatabase.createQuery(TaskDb.TABLE_NAME, TaskDb.SELECT_PENDING_TAKS)
                .map { it.run() }
                .map { it.mapTo(TaskDb.SELECT_PENDING_TASK_MAPPER) }
                .map { it.map { it.toTask() } }
    }

    override fun markTaskAsDone(id: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun markTaskAsPending(id: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun deleteATask(id: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun undoATask(id: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented")
    }

    private fun fetchTasksFromApi() {
        taskService.items()
                .map { it.data }
                .map { insert2Db(it) }
                .subscribe({
                    sharedPreferences.setLoaded(true)
                    Timber.d(it.toString())
                }, {
                    Timber.e(it, "Fetch tasks error")
                })
    }

    private fun insert2Db(tasks: List<TaskEntity>) {
        val transaction = briteDatabase.newTransaction()
        try {
            tasks.forEach {
                briteDatabase.insert(TaskDb.TABLE_NAME, TaskDb.FACTORY.marshal()
                        .id(it.id)
                        .name(it.name)
                        .state(it.isDone())
                        .asContentValues())
            }
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }
    }
}