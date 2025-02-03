package com.utzcoz.parser.dumpsys

class SurfaceFlingerParser {
    companion object {
        const val BUFFER_LAYER_TREE_COMMAND = "-bl-tree"
        const val SF_PARSER_NAME = "surfaceflinger"

        fun parseSurfaceFlingerDumpsys(input: String): SurfaceFlinger {
            val bufferLayers = extractBufferLayerPart(input).map { Layer.parseLayer(it) }
            val containerLayers = extractContainerLayerPart(input).map { Layer.parseLayer(it) }
            val colorLayers = extractColorLayerPart(input).map { Layer.parseLayer(it) }
            return SurfaceFlinger(bufferLayers + containerLayers + colorLayers)
        }

        private fun extractLayerPart(
            input: String,
            anchor: String,
        ): List<String> {
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

        private fun extractBufferLayerPart(input: String): List<String> = extractLayerPart(input, "+ BufferLayer")

        private fun extractContainerLayerPart(input: String): List<String> = extractLayerPart(input, "+ ContainerLayer")

        private fun extractColorLayerPart(input: String): List<String> = extractLayerPart(input, "+ ColorLayer")

        fun parse(
            subCommands: List<String>,
            content: String,
        ) {
            if (subCommands.contains(BUFFER_LAYER_TREE_COMMAND)) {
                val surfaceFlinger = parseSurfaceFlingerDumpsys(content)
                surfaceFlinger.dumpBufferLayerTree()
                return
            }
        }

        fun showSupportCommands(indent: String) {
            println("$indent$SF_PARSER_NAME:")
            println("$indent$indent$BUFFER_LAYER_TREE_COMMAND show buffer layer tree")
        }
    }
}
