package pl.edu.agh.eaiib.io.odis.monitoring

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet

/**
 * Listener for events from monitored network interfaces
 */
interface MonitorListener {

    /**
     * @param packet received packet
     * @param networkInterface interface on which package has been received
     */
    fun packetReceived(packet: Packet, networkInterface: NetworkInterface)
}