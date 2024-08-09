package com.github.avayne2.uuidgenerator.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.avayne2.uuidgenerator.Bundle
import com.github.avayne2.uuidgenerator.services.NotificationService
import com.github.avayne2.uuidgenerator.services.UUIDGeneratorService
import com.intellij.ui.components.JBTextArea
import javax.swing.JButton


class ToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val uuidGeneratorWindow = UUIDGeneratorWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(uuidGeneratorWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class UUIDGeneratorWindow(toolWindow: ToolWindow) {

        private val project = toolWindow.project

        private val service = UUIDGeneratorService

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val textField = JBTextArea().apply {
                isEditable = false
            }

            add(textField)
            add(JButton(Bundle.message("generate")).apply {
                addActionListener {
                    textField.text = service.generateUUIDV4()
                }
            })

            add(JButton(Bundle.message("copy")).apply {
                addActionListener {
                    if (textField.text.isEmpty()) {
                        NotificationService.notifyError(project, Bundle.message("empty"))
                        return@addActionListener
                    }
                    service.copyToClipboard(project, textField.text)
                }
            })
        }

    }
}
