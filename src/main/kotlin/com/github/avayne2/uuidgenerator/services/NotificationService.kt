package com.github.avayne2.uuidgenerator.services

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import java.util.Timer
import kotlin.concurrent.schedule

object NotificationService {
    private const val GROUP_ID = "UUID Generator Notifications"
    private const val EXPIRE_TIME_MILLISECONDS = 2000L // 2 seconds

    fun notifyInfo(project: Project?, content: String) {
        if (content.isEmpty()) return
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(content, NotificationType.INFORMATION)

        notification.notify(project)

        Timer().schedule(EXPIRE_TIME_MILLISECONDS) {
            notification.expire()
        }
    }

    fun notifyError(project: Project?, content: String) {
        if (content.isEmpty()) return
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup(GROUP_ID)
            .createNotification(content, NotificationType.ERROR)

        notification.notify(project)

        Timer().schedule(EXPIRE_TIME_MILLISECONDS) {
            notification.expire()
        }
    }
}