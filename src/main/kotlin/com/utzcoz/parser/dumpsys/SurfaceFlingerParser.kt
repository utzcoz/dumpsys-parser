package com.utzcoz.parser.dumpsys

class SurfaceFlingerParser {
    companion object {
        const val blTreeCommand = "-bl-tree"
        const val parserName = "surfaceflinger"

        fun parseSurfaceFlingerDumpsys(input: String): SurfaceFlinger {
            val bufferLayers = extractBufferLayerPart(input).map { Layer.parseLayer(it) }
            val containerLayers = extractContainerLayerPart(input).map { Layer.parseLayer(it) }
            val colorLayers = extractColorLayerPart(input).map { Layer.parseLayer(it) }
            return SurfaceFlinger(bufferLayers + containerLayers + colorLayers)
        }

        private fun extractLayerPart(input: String, anchor: String): List<String> {
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

        private fun extractBufferLayerPart(input: String): List<String> {
            return extractLayerPart(input, "+ BufferLayer")
        }

        private fun extractContainerLayerPart(input: String): List<String> {
            return extractLayerPart(input, "+ ContainerLayer")
        }

        private fun extractColorLayerPart(input: String): List<String> {
            return extractLayerPart(input, "+ ColorLayer")
        }

        fun parse(subCommands: List<String>, content: String) {
            if (subCommands.contains(blTreeCommand)) {
                val surfaceFlinger = parseSurfaceFlingerDumpsys(content)
                surfaceFlinger.dumpBufferLayerTree()
                return
            }
        }

        fun showSupportCommands(indent: String) {
            println("$indent$parserName:")
            println("$indent$indent$blTreeCommand show buffer layer tree")
        }
    }
}