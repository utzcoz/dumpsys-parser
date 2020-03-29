package com.utzcoz.parser.dumpsys

class SurfaceFlinger(val bufferLayers: List<BufferLayer>) {
    private val childMap = HashMap<BufferLayer, MutableSet<BufferLayer>>()

    // We use this field to store the layers without parent.
    private val rootLayers = HashSet<BufferLayer>()

    init {
        val map = HashMap<String, BufferLayer>()
        bufferLayers.forEach { map[it.name] = it }
        map.forEach { (_, value) ->
            val parent = map[value.parent]
            if (parent == null) {
                rootLayers.add(value)
            } else {
                var childSet = childMap[parent]
                if (childSet == null) {
                    childSet = HashSet()
                }
                childSet.add(value)
                childMap[parent] = childSet
            }
        }
        map.clear()
    }

    fun dumpBufferLayerTree() {
        for ((index, layer) in rootLayers.withIndex()) {
            var isLast = index == rootLayers.size - 1
            dumpBufferLayerBranch(layer, isLast, "")
        }
    }

    private fun dumpBufferLayerBranch(root: BufferLayer, isLast: Boolean, indent: String) {
        print(indent)
        if (isLast) {
            print("`-- ")
        } else {
            print("|-- ")
        }
        println(root.name)
        val newIndent = if (isLast) "$indent    " else "$indent|   "
        val childSet = childMap[root]
        childSet?.let {
            for ((index, child) in childSet.withIndex()) {
                val currentIsLast = index == childSet.size - 1
                dumpBufferLayerBranch(child, currentIsLast, newIndent)
            }
            // New indent is old indent pluses four space.
        }
    }
}