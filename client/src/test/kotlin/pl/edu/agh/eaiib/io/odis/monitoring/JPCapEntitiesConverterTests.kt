package pl.edu.agh.eaiib.io.odis.monitoring

import com.nhaarman.mockito_kotlin.mock
import io.github.benas.randombeans.api.EnhancedRandom.random
import org.junit.Assert.*
import org.junit.Test
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.impl.JPCapNetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.IPPacket
import pl.edu.agh.eaiib.io.odis.domain.packet.TCPPacket
import pl.edu.agh.eaiib.io.odis.monitoring.impl.JPCapEntitiesConverter
import java.lang.IllegalArgumentException

class JPCapEntitiesConverterTests {

    @Test
    fun testToJPCapInterface() {
        val jpcapInterface = mock<jpcap.NetworkInterface>()
        val networkInterface = JPCapNetworkInterface(jpcapInterface)

        assertEquals(jpcapInterface, JPCapEntitiesConverter.toJPCapInterface(networkInterface))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testToJPCapInterfaceWrongArgument() {
        val networkInterface = mock<NetworkInterface>()
        JPCapEntitiesConverter.toJPCapInterface(networkInterface)
    }

    @Test
    fun testFromJPCapInterface() {
        val jpcapInterface = mock<jpcap.NetworkInterface>()
        assertNotNull(JPCapEntitiesConverter.fromJPCapInterface(jpcapInterface))
    }

    @Test
    fun testFromJPCapPacket() {
        val packet = random(jpcap.packet.Packet::class.java, "datalink")
        assertNotNull(JPCapEntitiesConverter.fromJPCapPacket(packet))
    }

    @Test
    fun testFromJPCapTCPPacket() {
        val packet = random(jpcap.packet.TCPPacket::class.java, "datalink")
        assertTrue(JPCapEntitiesConverter.fromJPCapPacket(packet) is TCPPacket)
    }

    @Test
    fun testFromJPCapIPPacket() {
        val packet = random(jpcap.packet.IPPacket::class.java, "datalink")
        assertTrue(JPCapEntitiesConverter.fromJPCapPacket(packet) is IPPacket)
    }
}