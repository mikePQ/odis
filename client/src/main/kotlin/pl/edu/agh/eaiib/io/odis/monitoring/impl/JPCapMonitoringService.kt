package pl.edu.agh.eaiib.io.odis.monitoring.impl

import jpcap.JpcapCaptor
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringFilter
import java.util.function.Consumer

class JPCapMonitoringService : AbstractMonitoringService() {

    override fun startMonitoringInterfaceImpl(networkInterface: NetworkInterface, filter: MonitoringFilter) {
        val capture = JPCapInterfaceCapture.fromInterface(JPCapEntitiesConverter.toJPCapInterface(networkInterface))
        capture.addPacketConsumer(Consumer {
            val packet = JPCapEntitiesConverter.fromJPCapPacket(it)
            notifyPacketReceived(packet, networkInterface)
        })
        capture.start(filter)
    }

    override fun getAvailableInterfaces(): List<NetworkInterface> {
        val networkInterfaces = JpcapCaptor.getDeviceList()
        return networkInterfaces.map { JPCapEntitiesConverter.fromJPCapInterface(it) }
    }

    override fun findNetworkInterface(interfaceId: String): NetworkInterface? {
        return getAvailableInterfaces().firstOrNull { interfaceId == it.getId() }
    }

}
