package pl.edu.agh.eaiib.io.odis.monitoring

import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet
import pl.edu.agh.eaiib.io.odis.monitoring.impl.JPCapInterfaceCapture
import pl.edu.agh.eaiib.io.odis.monitoring.impl.JPCapMonitoringService

class JPCapMonitoringServiceTests {

    private lateinit var underTest: JPCapMonitoringService

    @Before
    fun setUp() {
        underTest = UnderTest()
    }

    @Test
    fun testGetAvailableInterfaces() {
        val availableInterfaces = underTest.getAvailableInterfaces()
        assertNotNull(availableInterfaces)
        assertEquals(1, availableInterfaces.size)
    }

    @Test
    fun testGetMonitoredInterfaces() {
        assertTrue(underTest.getMonitoredInterfaces().isEmpty())

        val id = "eth0"
        val networkInterface = underTest.findNetworkInterface(id)!!
        val filter = MonitoringFilter.EmptyFilter
        underTest.startMonitoringInterface(networkInterface, filter)

        assertEquals(1, underTest.getMonitoredInterfaces().size)
        assertEquals(networkInterface, underTest.getMonitoredInterfaces().first())
    }

    @Test
    fun testFindInterface() {
        val id = "eth0"
        val networkInterface = underTest.findNetworkInterface(id)
        assertNotNull(networkInterface)
        assertEquals(id, networkInterface!!.getId())
    }

    @Test
    fun testMonitorListeners() {
        val id = "eth0"
        val networkInterface = underTest.findNetworkInterface(id)!!

        val listener = mock<MonitorListener> {
            on { monitorsInterface(any()) } doReturn true
        }
        underTest.addMonitorListener(listener)
        val filter = MonitoringFilter.EmptyFilter
        underTest.startMonitoringInterface(networkInterface, filter)
        val packet = mock<Packet>()
        underTest.notifyPacketReceived(packet, networkInterface)
        verify(listener, times(1)).packetReceived(any(), any())
    }

    object TestDevicesProvider: JPCapMonitoringService.DevicesProvider {
        override fun getDevices(): Array<jpcap.NetworkInterface> {
            val networkInterface = mock<jpcap.NetworkInterface>()
            networkInterface.name = "eth0"
            return arrayOf(networkInterface)
        }
    }

    private class UnderTest: JPCapMonitoringService(TestDevicesProvider) {
        override fun createInterfaceCapture(networkInterface: NetworkInterface): JPCapInterfaceCapture {
            return JPCapInterfaceCapture(null)
        }
    }
}