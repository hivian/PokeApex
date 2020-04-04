package com.hivian.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.hivian.local.dao.PokedexDao
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PokedexDatabaseTest {

    @Mock
    lateinit var pokedexDatabase: PokedexDatabase
    @Mock
    lateinit var pokedexDao: PokedexDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun obtainPokedexDao() {
        doReturn(pokedexDao).whenever(pokedexDatabase).pokedexDao()

        assertThat(
            pokedexDatabase.pokedexDao(),
            instanceOf(PokedexDao::class.java)
        )
    }
}