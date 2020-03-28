package com.utzcoz.parser.dumpsys

open class Rect(val left: Int, val top: Int, val width: Int, val height: Int) {
    companion object {
        fun parseRect(input: String): Rect {
            val splits = input.split(",")
            if (splits.size != 4) {
                throw IllegalArgumentException("The valid rect string pattern is left, top, width, height")
            }
            return Rect(
                splits[0].trim().toInt(),
                splits[1].trim().toInt(),
                splits[2].trim().toInt(),
                splits[3].trim().toInt()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Rect) {
            return false
        }
        return left == other.left
                && top == other.top
                && width == other.width
                && height == other.height
    }

    override fun hashCode(): Int {
        return ((left * 32 + top) * 32 + width) * 32 + height
    }

    override fun toString(): String {
        return "Rect($left, $top, $width, $height)"
    }
}