package com.utzcoz.parser.dumpsys

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RectTest {
    @Test
    fun testParseRectWithValidString() {
        val input = "100, 200, 300, 400"
        val rect = Rect.parseRect(input)
        assertEquals(100, rect.left)
        assertEquals(200, rect.top)
        assertEquals(300, rect.right)
        assertEquals(400, rect.bottom)
    }

    @Test
    fun testParseRectWithInvalidString() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            val input = "100, 200, 300"
            Rect.parseRect(input)
        }
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

    @Test
    fun testIsValid() {
        val rect1 = Rect(100, 200, 300, 400)
        assertTrue(rect1.isValid())
        val rect2 = Rect(100, 200, 100, 400)
        assertFalse(rect2.isValid())
        val rect3 = Rect(100, 200, 100, 200)
        assertFalse(rect3.isValid())
    }

    @Test
    fun testIntersect() {
        val rect1 = Rect(100, 200, 300, 400)
        val rect2 = Rect(150, 150, 330, 250)
        assertEquals(Rect(150, 200, 300, 250), rect1.intersect(rect2))
        val rect3 = Rect(330, 150, 350, 400)
        assertFalse(rect1.intersect(rect3).isValid())
        val rect4 = Rect(110, 220, 250, 300)
        assertEquals(rect4, rect1.intersect(rect4))
    }
}
