package com.utzcoz.parser.dumpsys

class SurfaceFlingerParser {
    companion object {
        fun parseSurfaceFlingerDumpsys(input: String): SurfaceFlinger {
            val bufferLayers = extractBufferLayerPart(input).map { BufferLayer.parseBufferLayer(it) }
            return SurfaceFlinger(bufferLayers)
        }

        private fun extractBufferLayerPart(input: String): List<String> {
            val anchor = "+ BufferLayer"
            var left = input
            var currentIndex = left.indexOf(anchor)
            val bufferLayers = ArrayList<String>()
            while (currentIndex >= 0) {
                left = left.substring(currentIndex + anchor.length)
                currentIndex = left.indexOf(anchor)
                if (currentIndex >= 0) {
                    bufferLayers.add(anchor + left.substring(0, currentIndex))
                } else {
                    bufferLayers.add(anchor + left.substring(0, left.indexOf("Displays (")))
                }
            }
            return bufferLayers
        }

        fun parse(subCommands: List<String>) {
        }

        fun showSupportCommands(indent: String) {

        }
    }
}