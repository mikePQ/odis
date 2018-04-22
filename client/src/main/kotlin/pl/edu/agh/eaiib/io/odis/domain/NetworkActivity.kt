package pl.edu.agh.eaiib.io.odis.domain

import pl.edu.agh.eaiib.io.odis.domain.packet.Packet

class NetworkActivity
private constructor(private val packet: Packet,
                    private val networkInterface: NetworkInterface) {

    companion object {
        fun create(packet: Packet, networkInterface: NetworkInterface): NetworkActivity = NetworkActivity(packet, networkInterface)
    }
}