package com.hivian.repository

import com.hivian.model.mapper.Generations
import org.junit.Assert.assertEquals
import org.junit.Test


class GenerationsMapperTest {
    @Test
    fun `Get pokemon generation number from invalid pokemon id returns null`() {
        assertEquals(null, Generations.from(10023))
    }

    @Test
    fun `Get pokemon generation number from valid pokemon id returns generation number`() {
        assertEquals(1, Generations.from(50))
        assertEquals(2, Generations.from(152))
        assertEquals(3, Generations.from(386))
        assertEquals(4, Generations.from(401))
        assertEquals(5, Generations.from(495))
        assertEquals(6, Generations.from(718))
        assertEquals(7, Generations.from(807))
    }

}
