package com.lenguyenthanh.todoapp.ui.feature.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.model.Task
import com.lenguyenthanh.todoapp.ui.base.StatefulController

class TasksController : StatefulController<List<Task>>() {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.screen_tasks, container, false)
    }
}

