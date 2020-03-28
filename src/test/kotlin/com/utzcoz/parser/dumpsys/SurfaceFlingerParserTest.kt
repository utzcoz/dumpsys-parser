package com.utzcoz.parser.dumpsys

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class SurfaceFlingerParserTest {
    private lateinit var surfaceFlingerDumpsysString: String

    @Before
    fun setUp() {
        val path: Path =
            Paths.get("src", "test", "resources", "aosp-9-normal-dumpsys-surfaceflinger-result")
        val absolutePath: String = path.toFile().absolutePath
        surfaceFlingerDumpsysString = File(absolutePath).readText()
    }

    @Test
    fun testLoadTestStringSucceed() {
        Assert.assertNotEquals("", surfaceFlingerDumpsysString)
    }

    @Test
    fun parseSurfaceFlingerDumpsys() {
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        assertNotNull(surfaceFlinger)
        val bufferLayers = surfaceFlinger.bufferLayers
        assertEquals(36, bufferLayers.size)
        val firstBufferLayer = bufferLayers[0]
        assertEquals("Display Root#0", firstBufferLayer.name)
        assertEquals("none", firstBufferLayer.parent)
        assertEquals(0, firstBufferLayer.z)
        val middleBufferLayer = bufferLayers[bufferLayers.size / 2 - 1]
        assertEquals("WindowToken{1e95e8e android.os.BinderProxy@ff44e89}#0", middleBufferLayer.name)
        assertEquals("mAboveAppWindowsContainers#0", middleBufferLayer.parent)
        assertEquals(1, middleBufferLayer.z)
        val lastBufferLayer = bufferLayers[bufferLayers.size - 1]
        assertEquals("Display Overlays#0", lastBufferLayer.name)
        assertEquals("none", lastBufferLayer.parent)
        assertEquals(1, lastBufferLayer.z)
    }
}