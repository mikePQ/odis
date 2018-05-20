package pl.edu.agh.eaiib.io.odis.hosts

import io.github.benas.randombeans.api.EnhancedRandom.random
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesRepository
import pl.edu.agh.eaiib.io.odis.activities.InetAddress
import pl.edu.agh.eaiib.io.odis.activities.NetworkActivity
import java.util.*

class HostsServiceTests {

    @Mock
    private lateinit var activitiesRepository: ActivitiesRepository

    private lateinit var underTest: HostsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        underTest = HostsService(activitiesRepository)
    }

    @Test
    fun testGetAll() {
        val hosts = createHosts(100)
        val activities = createActivities(hosts)

        doReturn(activities).`when`(activitiesRepository).findAll()

        assertEquals(hosts, underTest.getAll())
    }

    @Test
    fun testGetByIp() {
        val hosts = createHosts(100)
        val activities = createActivities(hosts)

        doReturn(activities).`when`(activitiesRepository).findAll()

        hosts.forEach {
            assertEquals(it, underTest.getHostWithIp(it.ip).first())
        }
    }

    private fun createHost(): Host {
        return Host(random(String::class.java), random(String::class.java))
    }

    private fun createHosts(numberOfHosts: Int): List<Host> {
        return (1..numberOfHosts).map { createHost() }
    }

    private fun createActivities(hosts: List<Host>): List<NetworkActivity> {
        return hosts.map { createActivity(it) }
    }

    private fun createActivity(host: Host): NetworkActivity {
        val random = Random()
        val bytes = random.nextLong()
        val timestamp = random.nextLong()

        val srcAddress = InetAddress(host, random.nextInt(10000))
        val destAddress = InetAddress(host, random.nextInt(10000))

        return NetworkActivity(UUID.randomUUID().toString(), srcAddress, destAddress, bytes, timestamp)
    }
}