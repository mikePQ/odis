package pl.edu.agh.eaiib.io.odis.domain

import pl.edu.agh.eaiib.io.odis.domain.packet.Packet

class NetworkActivity
private constructor(val packet: Packet) {

    companion object {
        fun create(packet: Packet): NetworkActivity = NetworkActivity(packet)
    }
}