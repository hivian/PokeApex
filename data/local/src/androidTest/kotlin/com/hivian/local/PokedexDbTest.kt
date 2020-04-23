package com.hivian.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hivian.common_test.datasets.FakeData
import com.hivian.common_test.extensions.blockingObserve
import com.hivian.local.dao.PokedexDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PokedexDbTest {

    private lateinit var pokedexDatabase: PokedexDatabase
    private lateinit var pokedexDao: PokedexDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pokedexDatabase = Room.inMemoryDatabaseBuilder(context, PokedexDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        pokedexDao = pokedexDatabase.pokedexDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        pokedexDatabase.close()
    }

    @Test
    fun daoCall() {
        assertEquals(pokedexDatabase.pokedexDao(), pokedexDao)
    }

    @Test
    fun runSingleItemCRUD() {
        val fakeData = FakeData.createFakePokemonDb()
        runBlocking {
            // Insert
            val insertedId = pokedexDao.insert(fakeData)
            assertNotNull(insertedId)
            assertEquals(1, insertedId)

            // Find by name
            val findByName = pokedexDao.getPokemonByName(fakeData.name)
            assertNotNull(findByName)
            assertEquals(fakeData.name, findByName.name)

            // Update
            val new = findByName.apply { name = "Updated" }
            val numberOfRowUpdated = pokedexDao.update(new)
            assertEquals(1, numberOfRowUpdated)
            assertEquals(new.name, findByName.name)

            // Delete
            val numberOfRowDeleted = pokedexDao.delete(findByName)
            assertEquals(1, numberOfRowDeleted)
        }
    }

    @Test
    fun runMultipleItemCRUD() {
        val fakeDataList = FakeData.createFakePokemonsDb(5)
        runBlocking {
            // Insert all
            val insertedAllId = pokedexDao.insert(fakeDataList)
            assertNotNull(insertedAllId)
            assertEquals(fakeDataList.size, insertedAllId.size)

            // Find all
            val findAll = pokedexDao.getAllPokemonLive()
            assertNotNull(findAll)
            val findAllLiveValue = findAll.blockingObserve()
            assertNotNull(findAllLiveValue)
            assertEquals(fakeDataList.size, findAllLiveValue!!.size)

            // Delete all
            val numberOfRowDeleted = pokedexDao.deleteAll()
            assertEquals(fakeDataList.size, numberOfRowDeleted)
        }
    }

    @Test
    fun getPokemonListByPattern() {
        val fakeDataList = FakeData.createFakePokemonsDb(3)
        runBlocking {
            // Find by matching pattern
            pokedexDao.insert(fakeDataList)
            var matchingPattern = pokedexDao.getAllPokemonPatternLive("Name")
            assertNotNull(matchingPattern)
            var matchingPatternValue = matchingPattern.blockingObserve()
            assertNotNull(matchingPatternValue)
            assertEquals(3, matchingPatternValue!!.size)
            matchingPattern = pokedexDao.getAllPokemonPatternLive("Name_1")
            matchingPatternValue = matchingPattern.blockingObserve()
            assertNotNull(matchingPatternValue)
            assertEquals(1, matchingPatternValue!!.size)
        }
    }

    @Test
    fun upsertPokemons() {
        val fakeDataList = FakeData.createFakePokemonsDb(3)
        runBlocking {
            pokedexDao.insert(fakeDataList)
            val newFakeDataList = fakeDataList.mapIndexed { index, item ->
                item.apply { name = "Update_$index" }
            }
            pokedexDao.upsertDetail(newFakeDataList)
            val updatedData = pokedexDao.getAllPokemonLive()
            val matchingPatternValue = updatedData.blockingObserve()
            assertNotNull(matchingPatternValue)
            matchingPatternValue!!.forEach {
                assertTrue("Update" in it.name)
            }
        }
    }

}