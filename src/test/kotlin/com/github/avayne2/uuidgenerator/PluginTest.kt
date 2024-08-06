package com.github.avayne2.uuidgenerator

import com.github.avayne2.uuidgenerator.services.UUIDGeneratorService
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class PluginTest : BasePlatformTestCase() {

    fun testUUIDGeneration() {

        val service = UUIDGeneratorService

        assertNotSame(service.generateUUID(), service.generateUUID())
    }

    override fun getTestDataPath() = "src/test/testData"
}
