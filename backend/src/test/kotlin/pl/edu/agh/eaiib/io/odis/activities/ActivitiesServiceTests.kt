package pl.edu.agh.eaiib.io.odis.activities

import io.github.benas.randombeans.api.EnhancedRandom.random
import io.github.benas.randombeans.api.EnhancedRandom.randomStreamOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import kotlin.streams.toList

internal class ActivitiesServiceTests {

    @Mock
    private lateinit var activitiesRepository: ActivitiesRepository

    private lateinit var underTest: ActivitiesService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        underTest = ActivitiesService(activitiesRepository)
    }

    @Test
    fun testAddAll() {
        val elements = randomActivities(10)
        doReturn(elements).`when`(activitiesRepository).saveAll(elements)

        assertEquals(elements, underTest.addAll(elements))
    }

    @Test
    fun testGetAll() {
        val elements = randomActivities(100)
        doReturn(elements).`when`(activitiesRepository).findAll()

        assertEquals(elements, underTest.getAll(null))

        val elementsInDataRange = randomActivities(20)
        doReturn(elementsInDataRange).`when`(activitiesRepository).findByTimestampBetween(anyLong(), anyLong())

        assertEquals(elementsInDataRange, underTest.getAll(Pair(1 ,100)))
    }

    @Test
    fun testGetAssociatedActivities() {
        val ip = random(String::class.java)
        val elements = randomActivities(50)
        doReturn(elements).`when`(activitiesRepository).findActivitiesBySrcAddressHostIpOrDestAddressHostIp(ip, ip)

        assertEquals(elements, underTest.getAssociatedActivities(ip, null))

        val elementsInRange = randomActivities(15)
        doReturn(elementsInRange).`when`(activitiesRepository).findActivitiesWithSrcAddressHostIpInTimestampRange(ip, 1, 2)

        assertEquals(elementsInRange, underTest.getAssociatedActivities(ip, Pair(1, 2)))
    }

    private fun randomActivities(numberOfElements: Int): List<NetworkActivity> =
            randomStreamOf(numberOfElements, NetworkActivity::class.java).toList()
}