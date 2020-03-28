package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.Test

class RectTest {
    @Test
    fun testParseRectWithValidString() {
        val input = "100, 200, 300, 400"
        val rect = Rect.parseRect(input)
        assertEquals(100, rect.left)
        assertEquals(200, rect.top)
        assertEquals(300, rect.width)
        assertEquals(400, rect.height)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testParseRectWithInvalidString() {
        val input = "100, 200, 300"
        Rect.parseRect(input)
    }
}