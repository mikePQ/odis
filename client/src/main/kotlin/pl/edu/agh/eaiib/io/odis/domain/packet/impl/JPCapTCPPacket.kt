package pl.edu.agh.eaiib.io.odis.domain.packet.impl

import pl.edu.agh.eaiib.io.odis.domain.packet.TCPPacket

class JPCapTCPPacket(private val packet: jpcap.packet.TCPPacket): JPCapIPPacket(packet), TCPPacket {
    override fun getSourcePort(): Int = packet.src_port
    override fun getDestinationPort(): Int = packet.dst_port
}