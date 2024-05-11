package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class LayerTest {
    @Test
    fun testParseAOSP9BufferLayer() {
        val path: Path = Paths.get("src", "test", "resources", "aosp-9-buffer-layer")
        val absolutePath: String = path.toFile().absolutePath
        val bufferLayerTestString = File(absolutePath).readText()
        val layer: Layer = Layer.parseLayer(bufferLayerTestString)
        assertEquals("WindowToken{8747db android.os.BinderProxy@81008ea}#0", layer.name)
        assertEquals(Rect(5, 6, 7, 8), layer.visibleRegion)
        assertEquals(1, layer.layerStack)
        assertEquals(4, layer.z)
        assertEquals(Pair(20, 30), layer.position)
        assertEquals(Pair(3840, 3840), layer.size)
        assertEquals(1, layer.isOpaque)
        assertEquals("mAboveAppWindowsContainers#0", layer.parent)
    }

    @Test
    fun testParseAOSP10BufferLayer() {
        val path: Path = Paths.get("src", "test", "resources", "aosp-10-buffer-layer")
        val absolutePath: String = path.toFile().absolutePath
        val bufferLayerTestString = File(absolutePath).readText()
        val layer: Layer = Layer.parseLayer(bufferLayerTestString)
        assertEquals("com.android.systemui.ImageWallpaper#0", layer.name)
        assertEquals(Rect(-1, -1, -1, -1), layer.visibleRegion)
        assertEquals(1, layer.layerStack)
        assertEquals(4, layer.z)
        assertEquals(Pair(100, -202), layer.position)
        assertEquals(Pair(2880, 2560), layer.size)
        assertEquals(1, layer.isOpaque)
        assertEquals("a4d84df com.android.systemui.ImageWallpaper#0", layer.parent)
    }

    @Test
    fun testParseContainerLayer() {
        val path: Path = Paths.get("src", "test", "resources", "container-layer")
        val absolutePath: String = path.toFile().absolutePath
        val containerLayerTestString = File(absolutePath).readText()
        val layer: Layer = Layer.parseLayer(containerLayerTestString)
        assertEquals("WindowToken{74dc10f android.os.BinderProxy@4cbf9e9}#0", layer.name)
        assertEquals(Rect(-1, -1, -1, -1), layer.visibleRegion)
        assertEquals(1, layer.layerStack)
        assertEquals(2, layer.z)
        assertEquals(Pair(0, 0), layer.position)
        assertEquals(Pair(0, 0), layer.size)
        assertEquals(0, layer.isOpaque)
        assertEquals("mAboveAppWindowsContainers#0", layer.parent)
    }

    @Test
    fun testParseColorLayer() {
        val path: Path = Paths.get("src", "test", "resources", "color-layer")
        val absolutePath: String = path.toFile().absolutePath
        val colorLayerTestString = File(absolutePath).readText()
        val layer: Layer = Layer.parseLayer(colorLayerTestString)
        assertEquals("animation background stackId=0#0", layer.name)
        assertEquals(Rect(-1, -1, -1, -1), layer.visibleRegion)
        assertEquals(2, layer.layerStack)
        assertEquals(3, layer.z)
        assertEquals(Pair(0, 0), layer.position)
        assertEquals(Pair(0, 0), layer.size)
        assertEquals(0, layer.isOpaque)
        assertEquals("Stack=0#0", layer.parent)
    }
}
