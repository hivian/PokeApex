package com.hivian.remote.base

import com.hivian.remote.PokemonApiService
import com.hivian.remote.di.createRemoteModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest
import java.io.File

abstract class BaseTest: KoinTest {

    protected val pokemonApiService: PokemonApiService by inject()
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp(){
        configureMockServer()
        configureDi()
    }

    @After
    open fun tearDown(){
        this.stopMockServer()
        stopKoin()
    }

    // CONFIGURATION
    private fun configureDi(){
        startKoin {
            modules(listOf(createRemoteModule(mockServer.url("/").toString())))
        }
    }

    private fun configureMockServer(){
        mockServer = MockWebServer()
        mockServer.start()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri!!.path)
        return String(file.readBytes())
    }
}