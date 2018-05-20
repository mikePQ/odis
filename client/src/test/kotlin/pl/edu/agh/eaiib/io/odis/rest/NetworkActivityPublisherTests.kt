package pl.edu.agh.eaiib.io.odis.rest

import com.nhaarman.mockito_kotlin.mock
import io.github.benas.randombeans.api.EnhancedRandom.random
import io.reactivex.Flowable
import jpcap.packet.TCPPacket
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.impl.JPCapTCPPacket
import java.net.InetAddress

class NetworkActivityPublisherTests {

    private lateinit var underTest: UnderTest

    @Before
    fun setUp() {
        underTest = UnderTest("http://localhost:9091", 1)
    }

    @Test
    fun testPublishData() {
        val packet = createPacket()
        val networkInterface = mock<NetworkInterface>()
        underTest.packetReceived(packet, networkInterface)

        Thread.sleep(100)
        assertEquals(1, underTest.published.size)
    }

    private fun createPacket(): pl.edu.agh.eaiib.io.odis.domain.packet.TCPPacket {
        val jpcapPacket = random(TCPPacket::class.java, "datalink")
        jpcapPacket.src_ip = InetAddress.getLocalHost()
        jpcapPacket.dst_ip = InetAddress.getLocalHost()
        return JPCapTCPPacket(jpcapPacket)
    }

    class UnderTest(serverBaseUrl: String, publishPeriod: Int)
        : NetworkActivityPublisher(serverBaseUrl, publishPeriod) {

        val published = ArrayList<NetworkActivityDTO>()

        override fun publishImpl(activities: List<NetworkActivityDTO>): Flowable<Any> {
            published.addAll(activities)
            return Flowable.empty()
        }
    }
}