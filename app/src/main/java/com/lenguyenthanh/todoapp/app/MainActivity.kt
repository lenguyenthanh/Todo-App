package com.lenguyenthanh.todoapp.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.lenguyenthanh.todoapp.R
import com.lenguyenthanh.todoapp.common.extentions.setRoot
import com.lenguyenthanh.todoapp.ui.feature.todo.TasksController
import me.henrytao.mdcore.core.MdCore
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private var router: Router? = null
    lateinit var changeHandlerFrameLayout: ChangeHandlerFrameLayout
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        MdCore.init(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildActivityComponent()
        initializeView()
        initializeRouter(savedInstanceState)
    }

    private fun buildActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(TodoApp[this].getComponent())
                .activityModule(ActivityModule(this))
                .build()
        activityComponent.inject(this)
    }

    private fun initializeView() {
        changeHandlerFrameLayout = findViewById(R.id.controllerContainer) as ChangeHandlerFrameLayout
    }

    private fun initializeRouter(savedInstanceState: Bundle?) {
        Timber.d("initializeRouter ${savedInstanceState == null}")
        if (router == null) {
            router = Conductor.attachRouter(this, changeHandlerFrameLayout, savedInstanceState)
            router?.let {
                if (!it.hasRootController()) {
                    it.setRoot(rootController())
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!(router?.handleBack() ?: false)) {
            super.onBackPressed()
        }
    }

    private fun rootController(): Controller {
        return TasksController()
    }
}