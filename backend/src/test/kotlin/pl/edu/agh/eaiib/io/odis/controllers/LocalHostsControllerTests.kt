package pl.edu.agh.eaiib.io.odis.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.api.EnhancedRandom.random
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
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
import pl.edu.agh.eaiib.io.odis.hosts.Host
import pl.edu.agh.eaiib.io.odis.hosts.LocalHostsController
import pl.edu.agh.eaiib.io.odis.hosts.LocalHostsService
import kotlin.streams.toList

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class LocalHostsControllerTests {

    @Autowired
    private lateinit var controller: LocalHostsController

    @MockBean
    private lateinit var service: LocalHostsService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
        Assertions.assertThat(controller).isNotNull
    }

    @Test
    fun testGetAllHosts() {
        val hosts = randomHosts(100)
        doReturn(hosts).`when`(service).getAll()

        val mapper = ObjectMapper()
        val jsonString = mapper.writeValueAsString(hosts)

        mockMvc.perform(get("/api/localHosts"))
                .andExpect(status().isOk)
                .andExpect(content().json(jsonString))
    }

    @Test
    fun testSaveLocalHost() {
        val host = random(Host::class.java)
        val mapper = ObjectMapper()
        val hostJson = mapper.writeValueAsString(host)

        mockMvc.perform(post("/api/localHosts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(hostJson))
                .andExpect(status().isOk)
    }

    private fun randomHosts(numberOfElements: Int): List<Host> =
            EnhancedRandom.randomStreamOf(numberOfElements, Host::class.java).toList()
}