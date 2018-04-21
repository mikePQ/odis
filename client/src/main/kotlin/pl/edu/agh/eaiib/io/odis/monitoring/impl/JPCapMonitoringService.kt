package pl.edu.agh.eaiib.io.odis.monitoring.impl;

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

}
