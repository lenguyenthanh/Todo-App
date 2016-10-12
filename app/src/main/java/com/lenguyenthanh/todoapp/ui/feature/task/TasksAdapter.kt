package com.lenguyenthanh.todoapp.ui.feature.task

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.model.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(private val layoutInflater: LayoutInflater,
                   private val onItemClick: (Task) -> Unit) : RecyclerView.Adapter<TaskViewHolder>() {
    private var items = emptyList<Task>()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder? =
            TaskViewHolder(layoutInflater.inflate(R.layout.task_item, parent, false),
                    onItemClick)

    fun replaceData(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class TaskViewHolder(private val viewItem: View,
                     private val onItemClick: (Task) -> Unit) : RecyclerView.ViewHolder(viewItem) {
    fun bind(task: Task) = with(viewItem) {
        viewItem.setOnClickListener { onItemClick(task) }
        viewItem.text1.text = task.name
    }
}