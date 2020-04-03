package com.hivian.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserRepositoryTest {

/*
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    // FOR DATA
    private lateinit var observer: Observer<ResultWrapper<List<Pokemon>>>
    private lateinit var observerPokemon: Observer<ResultWrapper<Pokemon>>
    private lateinit var pokedexRepository: PokedexRepository
    private val pokemonService = mockk<PokemonDatasource>()
    private val pokedexDao = mockk<PokedexDao>(relaxed = true)
    private val mapper = mockk<MapperPokedexRepository>(relaxed = true)

    @Before
    fun setUp() {
        observer = mockk(relaxed = true)
        observerPokemon = mockk(relaxed = true)
        pokedexRepository = PokedexRepositoryImpl(pokemonService, pokedexDao, mapper)
    }

    @Test
    fun `Get top users from server when no internet is available`() {
        val exception = IOException("Internet")
        coEvery { pokemonService.fetchTopPokemonsAsync() } throws exception
        coEvery { pokedexDao.getTopPokemons() } returns listOf()
        every { mapper.dbToDomainMapper.map(listOf()) } returns listOf()

        runBlocking {
            pokedexRepository.getTopPokemonsWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(ResultWrapper.loading(null)) // Init loading with no value
            observer.onChanged(ResultWrapper.loading(listOf())) // Then trying to load from db (fast temp loading) before load from remote source
            observer.onChanged(ResultWrapper.networkError(exception, listOf())) // Retrofit 403 error
        }
        confirmVerified(observer)
    }


    @Test
    fun `Get top users from network`() {
        val fakePokemonsNetwork = FakeData.createFakePokemonsNetwork(5)
        coEvery { pokemonService.fetchTopPokemonsAsync() } returns ApiResult(fakePokemonsNetwork.size, "", "", fakePokemonsNetwork)
        val fakePokemonsDb = mapper.remoteToDbMapper.map(fakePokemonsNetwork)
        coEvery { pokedexDao.getTopPokemons() } returns listOf() andThen fakePokemonsDb
        val fakePokemonsDomain = MapperPokemonDbToDomainImpl().map(fakePokemonsDb)
        every { mapper.dbToDomainMapper.map(listOf()) } returns listOf()
        every { mapper.dbToDomainMapper.map(fakePokemonsDb) } returns fakePokemonsDomain

        runBlocking {
            pokedexRepository.getTopPokemonsWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(ResultWrapper.loading(null)) // Loading from remote source
            observer.onChanged(ResultWrapper.loading(listOf())) // Then trying to load from db (fast temp loading) before load from remote source
            observer.onChanged(ResultWrapper.success(fakePokemonsDomain)) // Success
        }


        coVerify(exactly = 1) {
            pokedexDao.save(fakePokemonsDb)
        }


        confirmVerified(observer)
    }

    @Test
    fun `Get top users from db`() {
        val fakePokemonsNetwork = FakeData.createFakePokemonsNetwork(5)
        coEvery { pokemonService.fetchTopPokemonsAsync() } returns ApiResult(fakePokemonsNetwork.size, "", "", fakePokemonsNetwork)
        val fakePokemonsDb = MapperPokemonRemoteToDbImpl().map(fakePokemonsNetwork)
        coEvery { pokedexDao.getTopPokemons() } returns fakePokemonsDb
        val fakePokemonsDomain = MapperPokemonDbToDomainImpl().map(fakePokemonsDb)
        every { mapper.dbToDomainMapper.map(fakePokemonsDb) } returns fakePokemonsDomain

        runBlocking {
            pokedexRepository.getTopPokemonsWithCache().observeForever(observer)
        }

        verifyOrder {
            observer.onChanged(ResultWrapper.loading(null)) // Loading from remote source
            observer.onChanged(ResultWrapper.success(fakePokemonsDomain))// Success
        }

        confirmVerified(observer)
    }

    @Test
    fun `Get user's detail from network`() {
        val fakePokemonNetwork = FakeData.createFakePokemonNetwork("fake_login")
        coEvery { pokemonService.fetchPokemonDetailAsync("fake_login") } returns fakePokemonNetwork
        val fakePokemonDb = mapper.remoteToDbMapper.map(fakePokemonNetwork)
        coEvery { pokedexDao.getPokemonByName("fake_login") } returns fakePokemonDb
        val fakePokemonDomain = MapperPokemonDbToDomainImpl().map(fakePokemonDb)
        every { mapper.dbToDomainMapper.map(fakePokemonDb) } returns fakePokemonDomain

        runBlocking {
            pokedexRepository.getPokemonDetailWithCache(name = "fake_login").observeForever(observerPokemon)
        }

        verify {
            observerPokemon.onChanged(ResultWrapper.loading(null)) // Loading from remote source
            observerPokemon.onChanged(ResultWrapper.loading(fakePokemonDomain)) // Then trying to load from db (fast temp loading) before load from remote source
            observerPokemon.onChanged(ResultWrapper.success(fakePokemonDomain)) // Success
        }


        coVerify(exactly = 1) {
            pokedexDao.save(fakePokemonDb)
        }


        confirmVerified(observerPokemon)
    }

    @Test
    fun `Get user's detail from db`() {
        val fakePokemonNetwork = FakeData.createFakePokemonNetwork("fake_login")
        coEvery { pokemonService.fetchPokemonDetailAsync("fake_login") } returns fakePokemonNetwork
        val fakePokemonDb = MapperPokemonRemoteToDbImpl().map(fakePokemonNetwork)
        coEvery { pokedexDao.getPokemonByName("fake_login") } returns fakePokemonDb.apply { lastRefreshed = Date() }
        val fakePokemonDomain = MapperPokemonDbToDomainImpl().map(fakePokemonDb)
        every { mapper.dbToDomainMapper.map(fakePokemonDb) } returns fakePokemonDomain

        runBlocking {
            pokedexRepository.getPokemonDetailWithCache(name = "fake_login").observeForever(observerPokemon)
        }

        verify {
            observerPokemon.onChanged(ResultWrapper.loading(null)) // Loading from remote source
            observerPokemon.onChanged(ResultWrapper.success(fakePokemonDomain)) // Success
        }

        confirmVerified(observerPokemon)
    }
*/

}