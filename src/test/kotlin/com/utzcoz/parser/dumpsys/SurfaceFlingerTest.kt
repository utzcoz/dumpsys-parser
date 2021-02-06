package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class SurfaceFlingerTest {
    private val outContent = ByteArrayOutputStream()
    private val originOutContent: PrintStream = System.out

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @After
    fun tearDown() {
        System.setOut(originOutContent)
    }

    @Test
    fun testAOSP9DumpBufferLayerTree() {
        val absolutePath: String = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val surfaceFlingerDumpsysString = File(absolutePath).readText()
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        surfaceFlinger.dumpBufferLayerTree()
        val expects = """
|-- Display Overlays#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
`-- Display Root#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |-- com.android.server.wm.DisplayContent__%__TaskStackContainers@8b527d1#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- Stack=0#0, isOpaque 0, region Rect(0, 0, 1920, 1080)
    |   |   |-- Task=43#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   |   `-- AppWindowToken{67b5d6b token=Token{637baba ActivityRecord{5470fe5 u0 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity t43}}}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   |       `-- 2e7b096 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   |           `-- com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0, isOpaque 0, region Rect(0, 0, 1920, 1080)
    |   |   `-- animation background stackId=0#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- animationLayer#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- boostedAnimationLayer#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- homeAnimationLayer#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   `-- splitScreenDividerAnchor#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |-- mAboveAppWindowsContainers#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- WindowToken{1e95e8e android.os.BinderProxy@ff44e89}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- b00b6af AssistPreviewPanel#0, isOpaque 0, region Rect(0, 1080, 3840, 4920)
    |   |-- WindowToken{4f36f0f android.os.BinderProxy@b4c3c6e}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- c170b9c com.farmerbb.taskbar.androidx86#0, isOpaque 0, region Rect(0, 1008, 3840, 4848)
    |   |-- WindowToken{85a4589 android.os.BinderProxy@3ead890}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- 95898e DockedStackDivider#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |-- WindowToken{8d12250 android.os.BinderProxy@d49a013}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- 3602c49 NavigationBar#0, isOpaque 0, region Rect(0, 1008, 3840, 4848)
    |   |       `-- NavigationBar#0, isOpaque 0, region Rect(0, 1008, 1920, 1080)
    |   |-- WindowToken{9c004 android.os.BinderProxy@e781417}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- 3c029ed com.farmerbb.taskbar.androidx86#0, isOpaque 0, region Rect(0, 918, 3840, 4758)
    |   |       `-- #0, isOpaque 0, region Rect(0, 918, 1920, 1008)
    |   |-- WindowToken{a22180c android.os.BinderProxy@aa273f}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |   `-- 9201555 StatusBar#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   |       `-- StatusBar#0, isOpaque 0, region Rect(0, 0, 1920, 36)
    |   `-- WindowToken{b01ed2b android.os.BinderProxy@703b17a}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |       `-- 78b6b46 com.farmerbb.taskbar.androidx86#0, isOpaque 0, region Rect(0, 36, 3840, 3876)
    |-- mBelowAppWindowsContainers#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |   `-- WallpaperWindowToken{2817237 token=android.os.Binder@9213336}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |       `-- 8b85d8d com.android.systemui.ImageWallpaper#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
    |           `-- com.android.systemui.ImageWallpaper#0, isOpaque 1, region Rect(-480, -740, 2400, 1820)
    `-- mImeWindowsContainers#0, isOpaque 0, region Rect(0, 0, 3840, 3840)
        `-- WindowToken{3827d8c android.os.Binder@a5eb2bf}#0, isOpaque 0, region Rect(0, 0, 3840, 3840)

        """.trimIndent().replace("__%__", "$")
        assertEquals(expects, outContent.toString())
    }

    @Test
    fun testAOSP10DumpBufferLayerTree() {
        val absolutePath: String = TestUtil.getAOSP10DumpsysSurfaceFlingerTestFilePath()
        val surfaceFlingerDumpsysString = File(absolutePath).readText()
        val surfaceFlinger = SurfaceFlingerParser.parseSurfaceFlingerDumpsys(surfaceFlingerDumpsysString)
        surfaceFlinger.dumpBufferLayerTree()
        val expects = """
|-- Display Overlays#0, isOpaque 0, region Rect(0, 0, 0, 0)
|-- Display Root#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |-- com.android.server.wm.DisplayContent__%__TaskStackContainers@47c43de#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- Stack=0#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |-- Task=2#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |   `-- AppWindowToken{be0610a token=Token{14c1d75 ActivityRecord{3e18880 u0 com.android.launcher3/.Launcher t2}}}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |       `-- 783e122 com.android.launcher3/com.android.launcher3.Launcher#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |           `-- com.android.launcher3/com.android.launcher3.Launcher#0, isOpaque 0, region Rect(0, 0, 1400, 840)
|   |   |   `-- animation background stackId=0#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- Stack=10#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |-- Task=12#0, isOpaque 0, region Rect(441, 24, 441, 24)
|   |   |   |   `-- AppWindowToken{f7979b7 token=Token{15ea8b6 ActivityRecord{b354851 u0 com.android.messaging/.ui.conversationlist.ConversationListActivity t12}}}#0, isOpaque 0, region Rect(441, 24, 441, 24)
|   |   |   |       `-- 9c61f97 com.android.messaging/com.android.messaging.ui.conversationlist.ConversationListActivity#0, isOpaque 0, region Rect(401, -16, 401, -16)
|   |   |   |           `-- com.android.messaging/com.android.messaging.ui.conversationlist.ConversationListActivity#0, isOpaque 0, region Rect(401, -16, 893, 796)
|   |   |   `-- animation background stackId=10#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- Stack=11#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |-- Task=13#0, isOpaque 0, region Rect(599, 25, 599, 25)
|   |   |   |   `-- AppWindowToken{dcecfeb token=Token{2bf673a ActivityRecord{aa79e65 u0 com.android.dialer/.main.impl.MainActivity t13}}}#0, isOpaque 0, region Rect(599, 25, 599, 25)
|   |   |   |       `-- 6da9fcb com.android.dialer/com.android.dialer.main.impl.MainActivity#0, isOpaque 0, region Rect(559, -15, 559, -15)
|   |   |   |           `-- com.android.dialer/com.android.dialer.main.impl.MainActivity#0, isOpaque 0, region Rect(559, -15, 1051, 797)
|   |   |   `-- animation background stackId=11#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- Stack=12#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   |-- Task=14#0, isOpaque 0, region Rect(494, 54, 494, 54)
|   |   |   |   `-- AppWindowToken{d94b926 token=Token{e909481 ActivityRecord{30da868 u0 com.android.contacts/.activities.PeopleActivity t14}}}#0, isOpaque 0, region Rect(494, 54, 494, 54)
|   |   |   |       `-- 11452c2 com.android.contacts/com.android.contacts.activities.PeopleActivity#0, isOpaque 0, region Rect(454, 14, 454, 14)
|   |   |   |           `-- com.android.contacts/com.android.contacts.activities.PeopleActivity#0, isOpaque 0, region Rect(454, 14, 946, 826)
|   |   |   `-- animation background stackId=12#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- animationLayer#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- boostedAnimationLayer#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- homeAnimationLayer#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   `-- splitScreenDividerAnchor#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |-- mAboveAppWindowsContainers#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |-- WindowToken{68b3176 android.os.BinderProxy@1c03438}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   `-- 99a3377 AssistPreviewPanel#0, isOpaque 0, region Rect(0, 840, 0, 840)
|   |   |-- WindowToken{74dc10f android.os.BinderProxy@4cbf9e9}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   `-- daf5f2b StatusBar#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |       `-- StatusBar#0, isOpaque 0, region Rect(0, 0, 1400, 24)
|   |   |-- WindowToken{f5ba527 android.os.BinderProxy@8744a41}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   |   `-- 98163d4 NavigationBar0#0, isOpaque 0, region Rect(0, 792, 0, 792)
|   |   |       `-- NavigationBar0#0, isOpaque 0, region Rect(0, 792, 1400, 840)
|   |   `-- WindowToken{fec3981 android.os.BinderProxy@7526c8b}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |       `-- bdffa26 DockedStackDivider#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |-- mBelowAppWindowsContainers#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |   `-- WallpaperWindowToken{9825a75 token=android.os.Binder@a7bbdac}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |       `-- a4d84df com.android.systemui.ImageWallpaper#0, isOpaque 0, region Rect(0, 0, 0, 0)
|   |           `-- com.android.systemui.ImageWallpaper#0, isOpaque 1, region Rect(0, -202, 2880, 2358)
|   `-- mImeWindowsContainers#0, isOpaque 0, region Rect(0, 0, 0, 0)
|       `-- WindowToken{ed88a6 android.os.Binder@a94f201}#0, isOpaque 0, region Rect(0, 0, 0, 0)
|           `-- 6d3d337 InputMethod#0, isOpaque 0, region Rect(0, 24, 0, 24)
|-- Drag and Drop Input Consumer#0, isOpaque 0, region Rect(0, 0, 0, 0)
`-- Input Consumer recents_animation_input_consumer#1, isOpaque 0, region Rect(0, 0, 0, 0)

        """.trimIndent().replace("__%__", "$")
        assertEquals(expects, outContent.toString())
    }
}