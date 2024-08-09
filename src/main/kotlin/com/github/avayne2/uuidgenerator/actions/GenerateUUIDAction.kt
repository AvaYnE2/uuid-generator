package com.github.avayne2.uuidgenerator.actions

import com.github.avayne2.uuidgenerator.services.UUIDGeneratorService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GenerateUUIDAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val uuid = UUIDGeneratorService.generateUUIDV4()
        UUIDGeneratorService.copyToClipboard(project, uuid)
    }
}
