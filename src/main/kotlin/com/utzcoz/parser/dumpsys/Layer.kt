package com.utzcoz.parser.dumpsys

open class Layer(
    val name: String,
    val visibleRegion: Rect,
    val layerStack: Int,
    val z: Int,
    val position: Pair<Int, Int>,
    val size: Pair<Int, Int>,
    val isOpaque: Int,
    val parent: String?
) {
    companion object {
        // TODO update example for AOSP 10
        /**
         * Parse string as following pattern to [Layer].
         *
         * + BufferLayer (fe08278 some-package-name#0)
         *  Region TransparentRegion (this=75190f2ed208 count=1)
         *    [  0,   0,   0,   0]
         *  Region VisibleRegion (this=75190f2ed010 count=1)
         *    [  0,   0,   0,   0]
         *  Region SurfaceDamageRegion (this=75190f2ed088 count=1)
         *    [  0,   0,   0,   0]
         *      layerStack=   0, z=        0, pos=(0,36), size=(3840,3840), crop=[  0,   0,  -1,  -1], finalCrop=[  0,   0,  -1,  -1], isOpaque=0, invalidate=1, dataspace=Default, defaultPixelFormat=RGBx_8888, color=(0.000,0.000,0.000,1.000), flags=0x00000000, tr=[1.00, 0.00][0.00, 1.00]
         *      parent=WindowToken{8747db android.os.BinderProxy@81008ea}#0
         *      zOrderRelativeOf=none
         *      activeBuffer=[   0x   0:   0,Unknown/None], queued-frames=0, mRefreshPending=0, windowType=-1, appId=-1
         */
        fun parseLayer(input: String): Layer {
            if (!input.startsWith("+ BufferLayer")
                && !input.startsWith("+ ContainerLayer")
                && !input.startsWith("+ ColorLayer")) {
                throw IllegalArgumentException("Valid buffer layer string should start with + BufferLayer")
            }

            // Parse name
            val namePart = parseBracket(input)
            val name = namePart.first

            // Parse visible region
            val visibleRegion = parseVisibleRegion(input)

            // Parse layer stack
            val layerStack = parseLayerStack(input)

            // Parse z
            val z = parseZ(input)

            // Parse position
            val position = parsePosition(input)

            // Parse size
            val size = parseSize(input)
            // Parse isOpaque
            val isOpaque = parseOpaque(input)
            // Parse parent
            val parent = parseParent(input)
            return Layer(
                name,
                visibleRegion,
                layerStack,
                z,
                position,
                size,
                isOpaque,
                parent
            )
        }

        private fun parseBracket(input: String): Pair<String, String> {
            return parse(input, "(", ")")
        }

        private fun parseParent(input: String): String {
            val lines = splits(input)
            val parentLine = lines.first { it.startsWith("parent=") }
            return parentLine.substring(parentLine.indexOf("=") + 1)
        }

        private fun parse(input: String, left: String, right: String): Pair<String, String> {
            val leftIndex = input.indexOf(left)
            val rightIndex = input.indexOf(right)
            return Pair(
                input.substring(leftIndex + 1, rightIndex),
                input.substring(rightIndex + 1)
            )
        }

        private fun splits(input: String): List<String> {
            return input.lines().map { it.trimIndent() }.filter { it.isNotBlank() }
        }

        private fun parseVisibleRegion(input: String): Rect {
            // Find the first index of "Region VisibleRegion"
            val anchor = "Region VisibleRegion"
            val length = anchor.length
            var startIndex = input.indexOf(anchor)
            startIndex = input.indexOf(")", startIndex + length) + 1
            val endIndex = input.indexOf("Region SurfaceDamageRegion")
            var rectStr = input.substring(startIndex, endIndex).trim()
            if (rectStr.startsWith("[") && rectStr.endsWith("]")) {
                rectStr = rectStr.substring(1, rectStr.length - 1)
                return Rect.parseRect(rectStr)
            }
            return Rect(-1, -1, -1, -1)
        }

        private fun parseSimpleInt(input: String, anchor: String): Int {
            val startIndex = input.indexOf(anchor) + anchor.length
            val endIndex = input.indexOf(",", startIndex)
            return input.substring(startIndex, endIndex).trim().toInt()
        }

        private fun parseLayerStack(input: String): Int {
            return parseSimpleInt(input, "layerStack=")
        }

        private fun parseZ(input: String): Int {
            return parseSimpleInt(input, "z=")
        }

        private fun parsePosition(input: String): Pair<Int, Int> {
            val anchor = "pos=("
            val startIndex = input.indexOf(anchor) + anchor.length
            val endIndex = input.indexOf(")", startIndex)
            val values = input.substring(startIndex, endIndex).split(",")
            if (values.size == 2) {
                return Pair(Integer.parseInt(values[0]), Integer.parseInt(values[1]))
            }
            return Pair(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }

        private fun parseSize(input: String): Pair<Int, Int> {
            val anchor = "size=("
            val startIndex = input.indexOf(anchor) + anchor.length
            val endIndex = input.indexOf(")", startIndex)
            val values = input.substring(startIndex, endIndex).split(",")
            if (values.size == 2) {
                return Pair(values[0].trim().toInt(), values[1].trim().toInt())
            }
            return Pair(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }

        private fun parseOpaque(input: String): Int {
            return parseSimpleInt(input, "isOpaque=")
        }
    }
}