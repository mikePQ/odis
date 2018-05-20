package pl.edu.agh.eaiib.io.odis.hosts

import io.github.benas.randombeans.api.EnhancedRandom.random
import io.github.benas.randombeans.api.EnhancedRandom.randomListOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

class LocalHostsServiceTests {

    @Mock
    private lateinit var hostsRepository: LocalHostsRepository

    private lateinit var underTest: LocalHostsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        underTest = LocalHostsService(hostsRepository)
    }

    @Test
    fun testGetAll() {
        val hosts = randomListOf(50, Host::class.java)
        doReturn(hosts).`when`(hostsRepository).findAll()

        assertEquals(hosts, underTest.getAll())
    }

    @Test
    fun testSaveHost() {
        val host = random(Host::class.java)
        doReturn(host).`when`(hostsRepository).save(host)

        assertEquals(host, underTest.saveHost(host))
    }
}