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
import rx.schedulers.Schedulers
import timber.log.Timber

interface TasksLoader {
    fun fetchData()
    fun fetchDoneTasks(): Observable<List<Task>>
    fun fetchPendingTask(): Observable<List<Task>>
    fun createTask(name: String): Observable<Long>
    fun markTaskAsDone(id: Long, name: String): Observable<Int>
    fun markTaskAsPending(id: Long, name: String): Observable<Int>
    fun deleteATask(id: Long): Observable<Long>
    fun undoATask(id: Long): Observable<Long>
}

class TasksLoaderImpl(private val briteDatabase: BriteDatabase, private val taskService: TaskService, private val sharedPreferences: SharedPreferences) : TasksLoader {

    override fun fetchData() {
        if (!sharedPreferences.isLoaded()) {
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

    override fun createTask(name: String): Observable<Long> {
        return getMaxId()
                .first()
                .flatMap {
                    Observable.fromCallable {
                        briteDatabase.insert(TaskDb.TABLE_NAME, TaskDb.FACTORY.marshal()
                                .id(it + 1)
                                .name(name)
                                .state(false)
                                .asContentValues())
                    }
                }
    }

    override fun markTaskAsDone(id: Long, name: String): Observable<Int> {
        return changeTaskState(id, name, isDone = true)
    }

    override fun markTaskAsPending(id: Long, name: String): Observable<Int> {
        return changeTaskState(id, name, isDone = false)
    }

    private fun changeTaskState(id: Long, name: String, isDone: Boolean): Observable<Int> {
        return Observable.fromCallable {
            briteDatabase.update(TaskDb.TABLE_NAME, TaskDb.FACTORY.marshal()
                    .id(id)
                    .state(isDone)
                    .name(name)
                    .asContentValues(), TaskDb.ID + " = ?", "$id")
        }
    }

    override fun deleteATask(id: Long): Observable<Long> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun undoATask(id: Long): Observable<Long> {
        throw UnsupportedOperationException("not implemented")
    }

    private fun fetchTasksFromApi() {
        taskService.items()
                .subscribeOn(Schedulers.io())
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

    private fun getMaxId(): Observable<Long> {
        return briteDatabase.createQuery(TaskDb.TABLE_NAME, TaskDb.SELECT_ALL)
                .map { it.run() }
                .map { it.mapTo(TaskDb.SELECT_DONE_TASK_MAPPER) }
                .map { it.map { it.id() } }
                .map { it.max() }
                .map { it ?: 0L }
    }
}