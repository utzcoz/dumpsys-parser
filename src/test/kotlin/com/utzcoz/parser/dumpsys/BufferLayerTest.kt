package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class BufferLayerTest {
    private lateinit var bufferLayerTestString: String

    @Before
    fun setUp() {
        val path: Path = Paths.get("src", "test", "resources", "buffer-layer")
        val absolutePath: String = path.toFile().absolutePath
        bufferLayerTestString = File(absolutePath).readText()
    }

    @Test
    fun testLoadTestStringSucceed() {
        assertNotEquals("", bufferLayerTestString)
    }

    @Test
    fun testParseBufferLayer() {
        val bufferLayer: BufferLayer = BufferLayer.parseBufferLayer(bufferLayerTestString)
        assertEquals("WindowToken{8747db android.os.BinderProxy@81008ea}#0", bufferLayer.name)
        assertEquals(Rect(1, 2, 3, 4), bufferLayer.transparentRegion)
        assertEquals(Rect(5, 6, 7, 8), bufferLayer.visibleRegion)
        assertEquals(Rect(9, 10, 11, 12), bufferLayer.damageRegion)
        assertEquals(1, bufferLayer.layerStack)
        assertEquals(4, bufferLayer.z)
        assertEquals(Pair(20, 30), bufferLayer.position)
        assertEquals(Pair(3840, 3840), bufferLayer.size)
        assertEquals(Rect(0, 0, -1, -1), bufferLayer.crop)
        assertEquals(Rect(0, 0, -1, -1), bufferLayer.finalCrop)
        assertEquals("mAboveAppWindowsContainers#0", bufferLayer.parent)
    }
}