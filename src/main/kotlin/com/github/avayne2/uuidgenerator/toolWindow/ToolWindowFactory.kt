package com.github.avayne2.uuidgenerator.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.avayne2.uuidgenerator.Bundle
import com.github.avayne2.uuidgenerator.services.NotificationService
import com.github.avayne2.uuidgenerator.services.UUIDGeneratorService
import com.intellij.ui.components.JBTextField
import javax.swing.*
import java.awt.BorderLayout
import java.awt.Component.CENTER_ALIGNMENT
import java.awt.Font

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

        fun getContent() = JBPanel<JBPanel<*>>(BorderLayout(10, 10)).apply {
            val titleLabel = JLabel(Bundle.message("label")).apply {
                horizontalAlignment = SwingConstants.CENTER
                font = font.deriveFont(Font.BOLD, 14f)
            }

            val textField = JBTextField().apply {
                isEditable = false
                horizontalAlignment = SwingConstants.CENTER
            }

            val buttonPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.X_AXIS)
                add(Box.createHorizontalGlue())

                val generateButton = JButton(Bundle.message("generate")).apply {
                    alignmentX = CENTER_ALIGNMENT
                    addActionListener {
                        textField.text = service.generateUUIDV4()
                    }
                }

                val generateAndCopyButton = JButton(Bundle.message("generateAndCopy")).apply {
                    alignmentX = CENTER_ALIGNMENT
                    addActionListener {
                        textField.text = service.generateUUIDV4()
                        service.copyToClipboard(project, textField.text)
                    }
                }

                val copyButton = JButton(Bundle.message("copy")).apply {
                    alignmentX = CENTER_ALIGNMENT
                    addActionListener {
                        if (textField.text.isEmpty()) {
                            NotificationService.notifyError(project, Bundle.message("empty"))
                            return@addActionListener
                        }
                        service.copyToClipboard(project, textField.text)
                    }
                }

                add(generateButton)
                add(Box.createHorizontalStrut(20))
                add(generateAndCopyButton)
                add(Box.createHorizontalStrut(20))
                add(copyButton)

                add(Box.createHorizontalGlue())
            }

            add(titleLabel, BorderLayout.NORTH)
            add(textField, BorderLayout.CENTER)
            add(buttonPanel, BorderLayout.SOUTH)
        }
    }
}