package com.utzcoz.parser.dumpsys

import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class SurfaceFlingerTest {
    private val outContent = ByteArrayOutputStream()
    private val originOutContent: PrintStream = System.out
    private lateinit var surfaceFlingerDumpsysString: String

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
        val absolutePath: String = TestUtil.getDumpsysSurfaceFlingerTestFilePath()
        surfaceFlingerDumpsysString = File(absolutePath).readText()
    }

    @After
    fun tearDown() {
        System.setOut(originOutContent)
    }

    @Test
    fun testDumpBufferLayerTree() {
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        surfaceFlinger.dumpBufferLayerTree()
        val expects = """
|-- Display Overlays#0
`-- Display Root#0
    |-- com.android.server.wm.DisplayContent__%__TaskStackContainers@8b527d1#0
    |   |-- Stack=0#0
    |   |   `-- Task=43#0
    |   |       `-- AppWindowToken{67b5d6b token=Token{637baba ActivityRecord{5470fe5 u0 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity t43}}}#0
    |   |           `-- 2e7b096 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0
    |   |               `-- com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0
    |   |-- animationLayer#0
    |   |-- boostedAnimationLayer#0
    |   |-- homeAnimationLayer#0
    |   `-- splitScreenDividerAnchor#0
    |-- mAboveAppWindowsContainers#0
    |   |-- WindowToken{1e95e8e android.os.BinderProxy@ff44e89}#0
    |   |   `-- b00b6af AssistPreviewPanel#0
    |   |-- WindowToken{4f36f0f android.os.BinderProxy@b4c3c6e}#0
    |   |   `-- c170b9c com.farmerbb.taskbar.androidx86#0
    |   |-- WindowToken{85a4589 android.os.BinderProxy@3ead890}#0
    |   |   `-- 95898e DockedStackDivider#0
    |   |-- WindowToken{8d12250 android.os.BinderProxy@d49a013}#0
    |   |   `-- 3602c49 NavigationBar#0
    |   |       `-- NavigationBar#0
    |   |-- WindowToken{9c004 android.os.BinderProxy@e781417}#0
    |   |   `-- 3c029ed com.farmerbb.taskbar.androidx86#0
    |   |       `-- #0
    |   |-- WindowToken{a22180c android.os.BinderProxy@aa273f}#0
    |   |   `-- 9201555 StatusBar#0
    |   |       `-- StatusBar#0
    |   `-- WindowToken{b01ed2b android.os.BinderProxy@703b17a}#0
    |       `-- 78b6b46 com.farmerbb.taskbar.androidx86#0
    |-- mBelowAppWindowsContainers#0
    |   `-- WallpaperWindowToken{2817237 token=android.os.Binder@9213336}#0
    |       `-- 8b85d8d com.android.systemui.ImageWallpaper#0
    |           `-- com.android.systemui.ImageWallpaper#0
    `-- mImeWindowsContainers#0
        `-- WindowToken{3827d8c android.os.Binder@a5eb2bf}#0

        """.trimIndent().replace("__%__", "$")
        assertEquals(expects, outContent.toString())
    }
}