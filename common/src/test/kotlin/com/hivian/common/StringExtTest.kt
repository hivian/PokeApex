package com.hivian.common

import com.hivian.common.extension.capitalize
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringExtTest {
    @Test
    fun `Capitalize empty String should return empty String`() {
        assertEquals("", "".capitalize())
    }

    @Test
    fun `Capitalize lowercased String should return capitalized String`() {
        assertEquals("Toto", "toto".capitalize())
        assertEquals("P", "p".capitalize())
        assertEquals("Well, well, well", "well, well, well".capitalize())
    }

    @Test
    fun `Capitalize uppercased String should return capitalized String`() {
        assertEquals("Toto", "Toto".capitalize())
        assertEquals("P", "P".capitalize())
        assertEquals("Well, well, well", "Well, well, well".capitalize())
        assertEquals("Good morning", "GOOD MORNING".capitalize())
        assertEquals("Good evening", "gOOD EVENING".capitalize())
    }
}
