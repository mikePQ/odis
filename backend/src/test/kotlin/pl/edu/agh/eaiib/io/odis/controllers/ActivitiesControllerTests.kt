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
        val activities =  randomActivities(100)
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
    fun testGetActivitiesForHostAndPeers() {
        val peersIps = random(String::class.java)

        mockMvc.perform(get("/api/activities")
                .param("peersIps", peersIps))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun testGetActivitiesForTimestampRange() {

    }

    private fun randomStrings(numberOfElements: Int): List<String> =
            EnhancedRandom.randomListOf(numberOfElements, String::class.java)

    private fun randomActivities(numberOfElements: Int): List<NetworkActivity> =
            EnhancedRandom.randomStreamOf(numberOfElements, NetworkActivity::class.java).toList()
}