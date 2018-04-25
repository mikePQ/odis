package pl.edu.agh.eaiib.io.odis.monitoring

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet

interface MonitorListener {
    fun packetReceived(packet: Packet, networkInterface: NetworkInterface)
    fun monitorsInterface(networkInterface: NetworkInterface): Boolean = true
}