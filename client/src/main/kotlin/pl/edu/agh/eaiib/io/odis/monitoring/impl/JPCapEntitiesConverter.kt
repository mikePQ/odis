package pl.edu.agh.eaiib.io.odis.monitoring.impl

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.impl.JPCapNetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet
import pl.edu.agh.eaiib.io.odis.domain.packet.impl.JPCapIPPacket
import pl.edu.agh.eaiib.io.odis.domain.packet.impl.JPCapPacket

object JPCapEntitiesConverter {
    fun toJPCapInterface(networkInterface: NetworkInterface): jpcap.NetworkInterface {
        if (networkInterface !is JPCapNetworkInterface) {
            throw IllegalArgumentException("Expected JPCap interface, but was: $networkInterface")
        }

        return networkInterface.jpcapInterface
    }

    fun fromJPCapInterface(jpcapInterface: jpcap.NetworkInterface): NetworkInterface
            = JPCapNetworkInterface(jpcapInterface)

    fun fromJPCapPacket(packet: jpcap.packet.Packet): Packet {
        val ipPacket = packet as? jpcap.packet.IPPacket ?: return JPCapPacket(packet)
        return JPCapIPPacket(ipPacket)
    }
}