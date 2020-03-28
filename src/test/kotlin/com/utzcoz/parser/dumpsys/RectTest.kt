package com.utzcoz.parser.dumpsys

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
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

    @Test
    fun testRectEquals() {
        val rect1 = Rect(100, 200, 300, 400)
        val rect2 = Rect(100, 200, 300, 400)
        assertEquals(rect1, rect2)
        val rect3 = Rect(100, 200, 300, 500)
        assertNotEquals(rect1, rect3)
    }

    @Test
    fun testRectHashCode() {
        val rect1 = Rect(100, 200, 300, 400)
        val rect2 = Rect(100, 200, 300, 400)
        assertEquals(rect1.hashCode(), rect2.hashCode())
    }

    @Test
    fun testRectToString() {
        val rect1 = Rect(100, 200, 300, 400)
        assertEquals("Rect(100, 200, 300, 400)", rect1.toString())
    }
}