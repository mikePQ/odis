package pl.edu.agh.eaiib.io.odis.config

import org.junit.Assert.*
import org.junit.Test
import pl.edu.agh.eaiib.io.odis.OdisClientApplication

class ConfigTests {

    @Test
    fun createDefaultConfig() {
        val configStream = OdisClientApplication::class.java.getResourceAsStream("/client.properties")
        val config = Config.fromInputStream(configStream)

        assertNotNull(config)
        assertEquals("http://localhost:9090/api/", config.serverBaseUrl)
        assertEquals(30000, config.publishPeriod)
        assertEquals("", config.monitoringFilter.getFilterConfig())
        assertEquals(1, config.monitoredInterfacesIds.size)
        assertEquals("", config.monitoredInterfacesIds.first())
    }

}