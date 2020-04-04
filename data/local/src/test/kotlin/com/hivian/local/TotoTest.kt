package com.hivian.local

import com.hivian.local.dao.PokedexDao
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TotoTest {

    private lateinit var appDatabase: PokedexDatabase

    @Before
    fun setUp() {
        appDatabase = mockk()
    }

    @Test
    fun `test Dao call`() {
        val myDao = mockk<PokedexDao>()
        every { appDatabase.pokedexDao() } returns myDao

        assertEquals(appDatabase.pokedexDao(), myDao)

        // The class I want to test, running with mocked Database and mocked Dao
        //SomeClass(appDatabase).run()

        coVerify { myDao.getTopPokemons() } // Test was called once
    }
}