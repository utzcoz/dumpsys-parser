package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class SurfaceFlingerParserTest {
    @Test
    fun parseAOSP9SurfaceFlingerDumpsys() {
        val absolutePath: String = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val surfaceFlingerDumpsysString = File(absolutePath).readText()
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        assertNotNull(surfaceFlinger)
        val layers = surfaceFlinger.layers
        assertEquals(37, layers.size)
    }

    @Test
    fun parseAOSP10SurfaceFlingerDumpsys() {
        val absolutePath: String = TestUtil.getAOSP10DumpsysSurfaceFlingerTestFilePath()
        val surfaceFlingerDumpsysString = File(absolutePath).readText()
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        assertNotNull(surfaceFlinger)
        val layers = surfaceFlinger.layers
        assertEquals(51, layers.size)
    }
}