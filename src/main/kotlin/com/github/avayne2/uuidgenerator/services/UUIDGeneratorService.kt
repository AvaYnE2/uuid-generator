package com.github.avayne2.uuidgenerator.services

import com.intellij.openapi.project.Project
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.UUID

object UUIDGeneratorService {
    fun generateUUID(): String = UUID.randomUUID().toString()
    fun copyToClipboard(project: Project,text: String) {
        val stringSelection = StringSelection(text)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(stringSelection, null)
        NotificationService.notifyInfo(project, text)
    }
}