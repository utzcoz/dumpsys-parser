package com.utzcoz.parser.dumpsys

class SurfaceFlinger(val layers: List<Layer>) {
    private val childMap = HashMap<Layer, MutableList<Layer>>()

    // We use this field to store the layers without parent.
    private val rootLayers = ArrayList<Layer>()

    init {
        val map = HashMap<String, Layer>()
        layers.forEach { map[it.name] = it }
        map.forEach { (_, value) ->
            val parent = map[value.parent]
            if (parent == null) {
                rootLayers.remove(value)
                rootLayers.add(value)
                rootLayers.sortBy { it.name }
            } else {
                var childList = childMap[parent]
                if (childList == null) {
                    childList = ArrayList()
                }
                childList.remove(value)
                childList.add(value)
                childList.sortBy { it.name }
                childMap[parent] = childList
            }
        }
        map.clear()
    }

    fun dumpBufferLayerTree() {
        for ((index, layer) in rootLayers.withIndex()) {
            val isLast = index == rootLayers.size - 1
            dumpBufferLayerBranch(layer, isLast, "")
        }
    }

    private fun dumpBufferLayerBranch(root: Layer, isLast: Boolean, indent: String) {
        print(indent)
        if (isLast) {
            print("`-- ")
        } else {
            print("|-- ")
        }
        val fullRegion = Rect(
            root.position.first,
            root.position.second,
            root.position.first + root.size.first,
            root.position.second + root.size.second
        )
        println("${root.name}, isOpaque ${root.isOpaque}, region $fullRegion")
        val newIndent = if (isLast) "$indent    " else "$indent|   "
        val childList = childMap[root]
        childList?.let {
            for ((index, child) in childList.withIndex()) {
                val currentIsLast = index == childList.size - 1
                dumpBufferLayerBranch(child, currentIsLast, newIndent)
            }
        }
    }
}