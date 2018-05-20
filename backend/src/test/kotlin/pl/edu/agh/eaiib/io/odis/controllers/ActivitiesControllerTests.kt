package pl.edu.agh.eaiib.io.odis.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.api.EnhancedRandom.random
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesController
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesService
import pl.edu.agh.eaiib.io.odis.activities.NetworkActivity
import java.util.*
import kotlin.streams.toList

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ActivitiesControllerTests {

    @Autowired
    private lateinit var controller: ActivitiesController

    @MockBean
    private lateinit var service: ActivitiesService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
        Assertions.assertThat(controller).isNotNull
    }

    @Test
    fun testAddActivities() {
        val activities = randomActivities(100)
        val mapper = ObjectMapper()
        mockMvc.perform(post("/api/activities")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(activities)))
                .andExpect(status().isOk)

        verify(service, times(1)).addAll(anyCollection())
    }

    @Test
    fun testGetAllActivities() {
        val activities = randomActivities(50)
        doReturn(activities).`when`(service).getAll(any())

        val mapper = ObjectMapper()
        mockMvc.perform(get("/api/activities"))
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(activities)))
    }

    @Test
    fun testGetActivitiesForHost() {
        val hostIp = random(String::class.java)
        val activities = randomActivities(20)
        doReturn(activities).`when`(service).getAssociatedActivities(anyString(), any())

        val mapper = ObjectMapper()
        mockMvc.perform(get("/api/activities")
                .param("hostIp", hostIp))
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(activities)))
    }

    @Test
    fun testGetActivitiesIllegalParameters() {
        val peersIps = random(String::class.java)

        mockMvc.perform(get("/api/activities")
                .param("peersIps", peersIps))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun testGetActivitiesForHostAndPeers() {
        val activities = randomActivities(20)
        doReturn(activities).`when`(service).getAssociatedActivities(anyString(), any())
        val hostIp = random(String::class.java)
        val srcIps = activities.map { it.srcAddress.host.ip }
        val destIps = activities.map { it.destAddress.host.ip }
        val peersIps = ArrayList(destIps)
        peersIps.addAll(srcIps)

        val mapper = ObjectMapper()
        mockMvc.perform(get("/api/activities")
                .param("peersIps", *peersIps.toTypedArray())
                .param("hostIp", hostIp))
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(activities)))
    }

    @Test
    fun testIllegalTimestampRange() {
        mockMvc.perform(get("/api/activities")
                .param("fromTime", System.currentTimeMillis().toString()))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun testGetActivitiesForTimestampRange() {
        val dataRange = Pair(randomTimestamp(), randomTimestamp())
        val activities = randomActivities(50)
        doReturn(activities).`when`(service).getAll(any())

        val mapper = ObjectMapper()
        mockMvc.perform(get("/api/activities")
                .param("fromTime", dataRange.first.toString())
                .param("toTime", dataRange.second.toString()))
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(activities)))
    }

    private fun randomTimestamp(): Long {
        return Random().nextLong()
    }

    private fun randomActivities(numberOfElements: Int): List<NetworkActivity> =
            EnhancedRandom.randomStreamOf(numberOfElements, NetworkActivity::class.java).toList()
}