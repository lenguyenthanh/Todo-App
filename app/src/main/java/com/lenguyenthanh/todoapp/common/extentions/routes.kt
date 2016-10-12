package com.lenguyenthanh.todoapp.common.extentions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler

fun Controller.goto(controller: Controller, pushHandler: ControllerChangeHandler = HorizontalChangeHandler(), popHandler: ControllerChangeHandler = HorizontalChangeHandler()) {
    router.pushController(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}

fun Controller.goto(controller: Controller, pushHandler: ControllerChangeHandler = HorizontalChangeHandler(), popHandler: ControllerChangeHandler = HorizontalChangeHandler(), router: Router) {
    router.pushController(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}

fun Controller.replace(controller: Controller, pushHandler: ControllerChangeHandler = HorizontalChangeHandler(), popHandler: ControllerChangeHandler = HorizontalChangeHandler()) {
    router.replaceTopController(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}

fun Controller.addChild(controller: Controller, container: Int, pushHandler: ControllerChangeHandler = FadeChangeHandler(), popHandler: ControllerChangeHandler = FadeChangeHandler()) {
    getChildRouter(container)
            ?.setRoot(RouterTransaction.with(controller)
                    .pushChangeHandler(pushHandler)
                    .popChangeHandler(popHandler))
}

fun Controller.addOverlayChild(controller: Controller, container: Int,
                               pushHandler: ControllerChangeHandler = FadeChangeHandler(),
                               popHandler: ControllerChangeHandler = FadeChangeHandler()) {
    getChildRouter(container)?.setPopsLastView(true)
            ?.setRoot(RouterTransaction.with(controller)
                    .pushChangeHandler(pushHandler)
                    .popChangeHandler(popHandler))

}

fun Router.setRoot(controller: Controller, pushHandler: ControllerChangeHandler = FadeChangeHandler(), popHandler: ControllerChangeHandler = FadeChangeHandler()) {
    setRoot(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}


fun Router.goto(controller: Controller, pushHandler: ControllerChangeHandler = HorizontalChangeHandler(), popHandler: ControllerChangeHandler = HorizontalChangeHandler()) {
    pushController(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}

fun Router.replace(controller: Controller, pushHandler: ControllerChangeHandler = HorizontalChangeHandler(), popHandler: ControllerChangeHandler = HorizontalChangeHandler()) {
    replaceTopController(RouterTransaction.with(controller)
            .pushChangeHandler(pushHandler)
            .popChangeHandler(popHandler))
}