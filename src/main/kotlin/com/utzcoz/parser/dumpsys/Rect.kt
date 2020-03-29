package com.utzcoz.parser.dumpsys

import kotlin.math.max
import kotlin.math.min

open class Rect(val left: Int, val top: Int, val right: Int, val bottom: Int) {
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
                && right == other.right
                && bottom == other.bottom
    }

    override fun hashCode(): Int {
        return ((left * 32 + top) * 32 + right) * 32 + bottom
    }

    override fun toString(): String {
        return "Rect($left, $top, $right, $bottom)"
    }

    fun isValid(): Boolean = right > left && bottom > top

    fun intersect(rect: Rect): Rect =
        Rect(
            max(left, rect.left),
            max(top, rect.top),
            min(right, rect.right),
            min(bottom, rect.bottom)
        )
}