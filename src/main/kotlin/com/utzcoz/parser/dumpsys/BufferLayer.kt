package com.utzcoz.parser.dumpsys

open class BufferLayer(
    val name: String,
    val transparentRegion: Rect,
    val visibleRegion: Rect,
    val damageRegion: Rect,
    val layerStack: Int,
    val z: Int,
    val position: Pair<Int, Int>,
    val size: Pair<Int, Int>,
    val crop: Rect,
    val finalCrop: Rect,
    val isOpaque: Boolean,
    val parent: String?
) {
    companion object {
        /**
         * Parse string as following pattern to [BufferLayer].
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
        fun parseBufferLayer(input: String): BufferLayer {
            if (!input.startsWith("+ BufferLayer")) {
                throw IllegalArgumentException("Valid buffer layer string should start with + BufferLayer")
            }
            var left = input
            // Parse name
            val namePart = parseBracket(left)
            val name = namePart.first
            left = namePart.second
            // Parse transparent region
            val transparentRegionPart = parseSquareBracket(left)
            val transparentRegion = transparentRegionPart.first
            left = transparentRegionPart.second
            // Parse visible region
            val visibleRegionPart = parseSquareBracket(left)
            val visibleRegion = visibleRegionPart.first
            left = visibleRegionPart.second
            // Parse damage region
            val damageRegionPart = parseSquareBracket(left)
            val damageRegion = damageRegionPart.first
            left = damageRegionPart.second
            // Parse layer stack
            val layerStackPart = parseAssignAndComma(left)
            val layerStack = layerStackPart.first
            left = layerStackPart.second
            // Parse z
            val zPart = parseAssignAndComma(left)
            val z = zPart.first
            left = zPart.second
            // Parse position
            val positionPart = parseBracket(left)
            val position = parseIntPair(positionPart.first)
            left = positionPart.second
            // Parse size
            val sizePart = parseBracket(left)
            val size = parseIntPair(sizePart.first)
            left = sizePart.second
            // Parse crop
            val cropPart = parseSquareBracket(left)
            val crop = cropPart.first
            left = cropPart.second
            // Parse final crop
            val finalCropPart = parseSquareBracket(left)
            val finalCrop = finalCropPart.first
            left = finalCropPart.second
            // Parse isOpaque
            // We should remove comma the previous part left.
            val isOpaquePart = parseAssignAndComma(left.replaceFirst(",", ""))
            val isOpaque = isOpaquePart.first.trim().toInt() != 0
            left = isOpaquePart.second
            // Parse parent
            val parent = parseParent(left)
            return BufferLayer(
                name,
                Rect.parseRect(transparentRegion),
                Rect.parseRect(visibleRegion),
                Rect.parseRect(damageRegion),
                layerStack.trim().toInt(),
                z.trim().toInt(),
                position,
                size,
                Rect.parseRect(crop),
                Rect.parseRect(finalCrop),
                isOpaque,
                parent
            )
        }

        private fun parseIntPair(input: String): Pair<Int, Int> {
            val splits = input.split(",")
            return Pair(splits[0].trim().toInt(), splits[1].trim().toInt())
        }

        private fun parseAssignAndComma(input: String): Pair<String, String> {
            return parse(input, "=", ",")
        }

        private fun parseBracket(input: String): Pair<String, String> {
            return parse(input, "(", ")")
        }

        private fun parseSquareBracket(input: String): Pair<String, String> {
            return parse(input, "[", "]")
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
    }
}