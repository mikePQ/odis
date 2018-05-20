package pl.edu.agh.eaiib.io.odis.monitoring.impl

import jpcap.JpcapCaptor
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringFilter
import java.util.function.Consumer

open class JPCapMonitoringService(private val devicesProvider: DevicesProvider = object : DevicesProvider {
    override fun getDevices(): Array<jpcap.NetworkInterface> = JpcapCaptor.getDeviceList()
}) : AbstractMonitoringService() {

    override fun startMonitoringInterfaceImpl(networkInterface: NetworkInterface, filter: MonitoringFilter) {
        val capture = createInterfaceCapture(networkInterface)
        capture.addPacketConsumer(Consumer {
            val packet = JPCapEntitiesConverter.fromJPCapPacket(it)
            notifyPacketReceived(packet, networkInterface)
        })
        capture.start(filter)
    }

    override fun getAvailableInterfaces(): List<NetworkInterface> {
        val networkInterfaces = devicesProvider.getDevices()
        return networkInterfaces.map { JPCapEntitiesConverter.fromJPCapInterface(it) }
    }

    override fun findNetworkInterface(interfaceId: String): NetworkInterface? {
        return getAvailableInterfaces().firstOrNull { interfaceId == it.getId() }
    }

    protected open fun createInterfaceCapture(networkInterface: NetworkInterface): JPCapInterfaceCapture {
        return JPCapInterfaceCapture.fromInterface(JPCapEntitiesConverter.toJPCapInterface(networkInterface))
    }

    interface DevicesProvider {
        fun getDevices(): Array<jpcap.NetworkInterface>
    }
}
