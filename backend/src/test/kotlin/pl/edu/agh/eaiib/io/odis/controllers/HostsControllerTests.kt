package pl.edu.agh.eaiib.io.odis.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.api.EnhancedRandom
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.agh.eaiib.io.odis.hosts.Host
import pl.edu.agh.eaiib.io.odis.hosts.HostsController
import pl.edu.agh.eaiib.io.odis.hosts.HostsService
import kotlin.streams.toList


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class HostsControllerTests {

    @Autowired
    private lateinit var controller: HostsController

    @MockBean
    private lateinit var service: HostsService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
        assertThat(controller).isNotNull
    }

    @Test
    fun testGetAllHosts() {
        val hosts = randomHosts(100)
        doReturn(hosts).`when`(service).getAll()

        val mapper = ObjectMapper()
        val jsonString = mapper.writeValueAsString(hosts)

        mockMvc.perform(get("/api/hosts"))
                .andExpect(status().isOk)
                .andExpect(content().json(jsonString))
    }

    private fun randomHosts(numberOfElements: Int): List<Host> =
            EnhancedRandom.randomStreamOf(numberOfElements, Host::class.java).toList()
}